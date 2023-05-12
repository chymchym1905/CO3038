import socket
import sys
from Adafruit_IO import MQTTClient, RequestError
import time
import random
from uart import *
from simpleai import *

AIO_FEED_ID = ["nut1", "nut2", "counterdata", "countervalue"]
AIO_USERNAME = "chymchym1905"
AIO_KEY = "aio_Zzom3558juSoops5Gg8R1M2Nm7gB"
i = "" #user input in message() from numpad on adafruit server
countervalue = 0 #value of random data interval from adafruit


def connected(client):
    client.publish("clientstatus", "ONLINE")
    for i in AIO_FEED_ID:
        client.subscribe(i)

def subscribe(client , userdata , mid , granted_qos):
    print("Subscribe thanh cong ...")

def disconnected(client):
    print("No internet connection. Waiting for connection to return...")


def message(client , feed_id , payload):
    if feed_id == "counterdata": #Triggers when user interact with numpad on server
        print("Nhan du lieu counter: "+ feed_id + " " + payload) #Numpad input value
        if payload!="*" and payload != "#": #Parse input (if input is 0-9) into a string
            global i
            i += payload
            print(i)
        elif payload == "*" or payload == "#":  #Update interval for random data if input is * or # on numpad
            client.publish("countervalue", int(i))
            i = ""   
    elif feed_id == "countervalue": #Update value of counter for random data
        global countervalue
        countervalue = payload
    elif feed_id == 'nut1':
        if payload == "0":
            writeData("OF1")
        else:
            writeData("ON1")
    elif feed_id == 'nut2':
        if payload == "0":
            writeData("OF2")
        else:
            writeData("ON2")
    else:   #print
        print("Nhan du lieu: "+ feed_id + " " + payload)

def on_exit():
    client.publish("clientstatus", "OFFLINE")
    client.disconnect()
    print("Last will message published")

def is_connected():
        # connect to the host -- tells us if the host is actually reachable
    try: 
        socket.create_connection(("io.adafruit.com", 80))
        return True
    except OSError as e:
        return False


client = MQTTClient(AIO_USERNAME , AIO_KEY)
import atexit
atexit.register(on_exit)
client.on_connect = connected
client.on_disconnect = disconnected
client.on_message = message
client.on_subscribe = subscribe
client.connect()
client.loop_background()

client.receive("countervalue")
counter = countervalue #counter for send data
# counter_ai = 5 #counter for ai data
time.sleep(5)
while True:
    
    if not is_connected():  #this code used to check internet connection
        client.disconnect()
        while not is_connected():
            time.sleep(1)
        print("Connection returned")
        client = MQTTClient(AIO_USERNAME , AIO_KEY)
        atexit.register(on_exit)
        client.on_connect = connected
        client.on_disconnect = disconnected
        client.on_message = message
        client.on_subscribe = subscribe
        client.connect()
        client.loop_background()
    # counter_ai -=1
    counter -= 1
    if counter <= 0: #Publish random data on server
        client.receive("countervalue") # Update the value of variable "countervalue" in program
        counter = int(countervalue) #Assign the value to the counter
        print(counter)
        temp = random.randint(15,60)
        light = random.randint(0, 500)
        humid = random.randint(0,100)
        print("Sending temp: "+ str(temp))
        print("Sending light: "+ str(light))
        print("Sending humid: "+ str(humid))
        aioutput = image_classification()
        client.publish("cambien1", temp)
        client.publish("cambien2", light)
        client.publish("cambien3", humid)
        client.publish("ai", aioutput)
    # if counter_ai <=0: #Publish ai data on server
    #     counter_ai = 5
        
    if check_serial_connection(ser):
        readSerial(client)
    time.sleep(1)
    pass