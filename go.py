import sys
import socket
from retrying import retry
from scapy.all import *
from scapy.layers import inet
from StringIO import StringIO
from scapy.all import StreamSocket, Raw

def checkPacket(pack):
    if "flags=U"  not in str(pack).replace(" ",""):
        return False
    else:
        return True

def sendFlag(ip):
	s = socket.socket()
	s.connect((ip, 2997))
	ss = StreamSocket(s, Raw)
	ss.send(Raw("Flag is: l0053_lips_m1ght_s1nk_sh1ps "))
	s.close()

while(True):
	sniffer = sniff(count=1, filter="tcp and port 1337")
	captureString = StringIO()
	save_stdout = sys.stdout
	sys.stdout = captureString
	sniffer[0].show()
	sys.stdout = save_stdout
	packetInfo = captureString.getvalue()
	ip = packetInfo[packetInfo.index("src=")+4:packetInfo.index("dst=")].replace(" ", "").replace("\n","")
	if checkPacket(packetInfo)==True:
		try:
	    		sendFlag(ip)
		except:
			pass
	else:
    		print "Try harder"
