class HttpRequest:
    POST = 'POST'
    GET = 'GET'
    DELETE = 'DELETE'

    def __init__(self):
        pass

    def parseRequest(self, request):
        req = request.split(' ')
        l = len(req)
        if not(l == 4 or l == 6):
            return
        
        print('in httpRequest obj: ', request)
        if l == 4:
            httpMtd, path, contentLen, contentBody = req
        else:
            httpMtd, path, _, contentLen, _, contentBody = req

        httpMtd, path, contentLen, contentBody = self.formatReq(httpMtd, path, contentLen, contentBody)

        _, prefix, key = path.split('/')
        
        return httpMtd, prefix, key, contentLen, contentBody

   
    def formatReq(self, httpMtd, path, contentLen, contentBody):
        httpMtd = httpMtd.upper()
        return httpMtd, path, contentLen, contentBody


            
