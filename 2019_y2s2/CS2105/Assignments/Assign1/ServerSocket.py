from socket import socket, AF_INET, SOCK_STREAM

class ServerSocket:

    def __init__(self, port_num):
        self.socket = socket(AF_INET, SOCK_STREAM)
        self.port_num = int(port_num)
        self.socket.bind(('', self.port_num))
