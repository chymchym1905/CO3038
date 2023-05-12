import time
import serial.tools.list_ports

def getPort():
    ports = serial.tools.list_ports.comports() #COM4, COM5
    # commPort = "None"
    for i in ports:
        strPort = str(i)
        if "com0com - serial port emulator" in strPort: #get port on PC
            splitPort = strPort.split(" ")
            commPort = (splitPort[0])
            # print(commPort)
        #getting the last port in the port list
    return "COM6" #COM5



def check_serial_connection(ser):
    if not ser.isOpen():
        print(f"Serial connection to {port} is closed.")
        ser.open()
        print(f"Serial connection to {port} is reopened.")
        return False
    else:
        # print(f"Serial connection to {port} is opened.")
        return True


port = getPort()
if port != "None":
    ser = serial.Serial( port, baudrate=115200)
    # ser = serial.Serial()
    print(ser)

# ser2 = serial.Serial("COM6", baudrate=115200)
# print(ser2)
# print(ser2.readline())



def processData(client, data):
    data = data.replace("!", "")
    data = data.replace("#", "")
    splitData = data.split(":")
    print(splitData)
    if splitData[1] == "T":
        client.publish("cambien1", splitData[2])
    if splitData[1] == "L":
        client.publish("cambien2", splitData[2])
    if splitData[1] == "H":
        client.publish("cambien3", splitData[2])
    


def readSerial(client):
    mess = ""
    bytesToRead = ser.inWaiting()
    if (bytesToRead > 0):
        mess = mess + ser.read(bytesToRead).decode("UTF-8")
        print(mess)
        while ("#" in mess) and ("!" in mess):
            start = mess.find("!")
            end = mess.find("#")
            # print("Sending data: " + mess[start:end + 1])
            processData(client, mess[start:end + 1])
            if (end == len(mess)):
                mess = ""
            else:
                mess = mess[end+1:]

def writeData(data):
    ser.write(str(data).encode('utf-8'))