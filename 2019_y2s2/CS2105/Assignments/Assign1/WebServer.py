from sys import argv
from ServerSocket import ServerSocket
from HttpRequest import HttpRequest
import os

class WebServer:
    KEY = 'key'
    FILE = 'file'
    OK_STATUS = "200 OK"
    MISSING_STATUS = "404 NOTFOUND"
    PERM_STATUS = "403 PERMISSIONERR"

    def __init__(self):
        port_num = self.getServerPort()
        print('listening on port ' + port_num)
        self.dictionary = {}
        self.start(port_num)
    
    def getServerPort(self):
        return argv[1]
    
    # create ServerSocket and listen to new connection requests
    def start(self, port_num):
        self.serverSocket = ServerSocket(port_num).socket
        
        while 1:
            self.handleClientSocket()
        
    # handles a HTTP request from a client
    # a new request obj is created for every HttpRequest
    def handleClientSocket(self):
        self.serverSocket.listen(1)
        connectionSocket, _ = self.serverSocket.accept()
        requestMsg = connectionSocket.recv(1024)
        if len(requestMsg) == 0:
            print('Received 0 bytes, closing socket')
            connectionSocket.close()

        requestObj = HttpRequest()
        parsed = requestObj.parseRequest(requestMsg)
        if parsed is None:
            return 
        httpMtd, prefix, key, contentLen, contentBody = parsed

        # truncate contents
        if contentLen < len(contentBody):
            print('truncating contents')
            contentBody = contentBody[:contentLen]

        print("parsed HttpRequest: ", httpMtd, prefix, key, contentLen, contentBody)
        response = self.formHttpResponse(httpMtd, prefix, key, contentLen, contentBody)
        self.sendHttpResponse(connectionSocket, response)
    
    def sendHttpResponse(self, connectionSocket, response):
        connectionSocket.send(response)

    def formHttpResponse(self, httpMtd, prefix, key, contentLen, contentBody):
        # only handle valid requests
        if not (prefix == WebServer.KEY or prefix == WebServer.FILE):
          return
        print('Forming http response: ' , httpMtd, prefix, key,
        contentLen, contentBody) 
        if prefix == WebServer.KEY:
            status, clen, cbody = self.handleKeyValReq(
                httpMtd, key, contentLen, contentBody)
        elif prefix == WebServer.FILE:
            status, clen, cbody = self.handleStaticFileReq(
                httpMtd, key, contentLen, contentBody)
        print(status, clen, cbody)
        if clen != 0:
            response = status + " content-length " + `clen` + "  " + cbody
        else:
            response = status + "  "
        return response


    def handleKeyValReq(self, httpMtd, key, contentLen, contentBody):
        status = WebServer.OK_STATUS    # default status 
        clen = 0
        cbody = ""

        if httpMtd == HttpRequest.POST:
            if len(contentLen) == 0 or len(contentBody) == 0:
                return
            self.dictionary[key] = contentBody

        elif httpMtd == HttpRequest.GET and len(contentBody) == 0:
            if key not in self.dictionary:
                status = WebServer.MISSING_STATUS
            else:
                # send status, content length of value, content body of value
                cbody = self.dictionary.get(key)
                clen = len(cbody)

        elif httpMtd == HttpRequest.DELETE and len(contentBody) == 0:
            if key not in self.dictionary:
                status = WebServer.MISSING_STATUS
            else:
                cbody = self.dictionary.pop(key)
                # send status, content length of deleted val, deleted value string
                clen = len(cbody)
        
        return status, clen, cbody


    def handleStaticFileReq(self, httpMtd, filePath, contentLen, contentBody):
        clen = 0
        cbody = ""

        # file does not exist
        if not os.path.isfile(filePath):
            status = WebServer.MISSING_STATUS
        else: 
            try:
              fd = open(filePath, 'r')
              cbody = fd.read()
              clen = len(cbody)
              status = WebServer.OK_STATUS
            except IOError:
              status = WebServer.PERM_STATUS
        return status, clen, cbody

WebServer()
