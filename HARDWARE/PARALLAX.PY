import serial
import re
import pyrebase
from twilio.rest import Client
import requests
import re
from bs4 import BeautifulSoup
import pandas as pd

page=requests.get("http://api.openweathermap.org/data/2.5/weather?q=Vadodara&appid=dab4298b5adf1eac9a04e7d89cee6b4d")
soup=BeautifulSoup(page.text,'html.parser')
soup=str(soup)
press=re.findall('"pressure":[0-9]+',soup)
press=re.findall('[0-9]+',press[0])
press=press[0]+' mmHg'
# Your Account SID from twilio.com/console
account_sid = "AC01eef4ed31cd534f2415e7e7067fa440"
# Your Auth Token from twilio.com/console
auth_token  = "ab550cfacb7e5d5568559a8a56851de0"

client = Client(account_sid, auth_token)

config = {
"apiKey": "AIzaSyDr7-D8Inrh-d97J7b22tJRLA6bn-ulOcw",
    "authDomain": "parallax-50dc4.firebaseapp.com",
    "databaseURL": "https://parallax-50dc4.firebaseio.com",
    "projectId": "parallax-50dc4",
    "storageBucket": "parallax-50dc4.appspot.com",
    "messagingSenderId": "1071807398260",
    "appId": "1:1071807398260:web:9ebb59dec41446fba72c21",
    "measurementId": "G-BS79YXCCY6"
  }

firebase = pyrebase.initialize_app(config)

db = firebase.database()
try:
    arduino=serial.Serial("COM3",timeout=1)
except:
    print("please check port")
while True:
    a=str(arduino.readline())
    d=re.findall('[0-9]+',a)
    
    #flow=re.findall("Flow rate: [0-9]+",a)
    if(len(d)==4):
        print(d)
        if(int(d[0])<=5):
            alam=5-int(d[0])
        else:
            alam=0
        wper=round((15-int(d[0]))/15*100,2)
        wper=str(wper)+' %'
        data = {"Water":wper,"Inflow":d[1]+' L per min',"IFml":press,"TFL":str(int(d[2])/1000)+' L',"level":str(alam)}
        db.child("Sensors").set(data)
    if(alam>=4):
        message = client.messages.create(to="+919370704718",from_="+12055288279",body="HIGH FLOOD ALERT evacuate your home!!")