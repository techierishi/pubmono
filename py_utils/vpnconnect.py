import time

import json
from pywinauto import taskbar
from pywinauto import timings
import pywinauto.keyboard as keyboard
import pywinauto
import pyautogui as pygui
from pywinauto.application import Application
import pyperclip
from pywinauto.keyboard import SendKeys

def getProps():
    data = json.load(open('data.json'))
    return data['rsa_passcode'] , data['vpn_password'], data['delay']

def getRSAToken(rsa_passcode):
    #"C:\Program Files (x86)\RSA SecurID Software Token\SecurID.exe"
    # first get the token from rsa
    app = Application().Start(cmd_line=u'"C:\\Program Files (x86)\\RSA SecurID Software Token\\SecurID.exe" ')
    qwidget = app.QWidget
    #print ("Here");
    qwidget.Wait('ready')

    qwidget.ClickInput()
    for k in rsa_passcode:
        # print (k)
        keyboard.SendKeys(k)
        
    x, y = pygui.position()
    # qwidget.MoveMouseInput(coords=(x + 80, y), pressed=u'', absolute=True)
    qwidget.ClickInput(coords=(x + 80, y), absolute=True)
    # pygui.click()
    print (pygui.position())
    # qwidget.MoveMouseInput(coords=(x + 60, y - 15), pressed=u'', absolute=True)
    qwidget.ClickInput(coords=(x + 65, y + 37), absolute=True)
    password =  pyperclip.paste()
    # print (pygui.position())
    # print((x + 100, y-60))
    # Click on cross to close the app
    qwidget.ClickInput(coords=(x + 100, y-60), absolute=True)

    return password

def connectToCiscoVpn(_rsaToken,vpn_password,delay):
    taskbar.ClickSystemTrayIcon('Cisco AnyConnect', exact=True, double=True)

    try:
        pwa_app = pywinauto.Application().connect(title='Cisco AnyConnect Secure Mobility Client')
        pwa_app.window(title=u'Cisco AnyConnect Secure Mobility Client', top_level_only=True).child_window(class_name='#32770').ConnectButton.Click()
    except:
        print(" Skipping some errors here")

    # print(delay)
    time.sleep( delay )
    pwa_app = pywinauto.Application().connect(title='Cisco AnyConnect Secure Mobility Client')
    cisco_window = pwa_app.window(title=u'Cisco AnyConnect | VPN-AA', top_level_only=True)
    cisco_window['Passcode:Edit'].set_text(_rsaToken)
    cisco_window['Second Password:Edit'].set_text(vpn_password)
    cisco_window.OKButton.Click()
    time.sleep( 5 )
    cisco_window = pwa_app.window(title=u'Cisco AnyConnect', top_level_only=True)
    cisco_window.AcceptButton.Click()
    #.PrintControlIdentifiers()

def doConnection():
    rsa_passcode,vpn_password,delay =  getProps()
    # print(rsa_passcode,vpn_password,delay)
    rsa_token = getRSAToken(rsa_passcode)
    connectToCiscoVpn(rsa_token,vpn_password,delay)

doConnection()
