/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "Shield - Press 18 Buttons", namespace: "Bart", author: "Bart") {
		capability "waterSensor"
        capability "Actuator"
		capability "Button"
		capability "Sensor"
        capability "Switch Level"	
		capability "Switch"
		capability "Battery"
		capability "Refresh"
        capability "Polling"
        capability "Configuration"
        
        command "push1"
        command "push2"
        command "push3"
        command "push4"
        command "push5"
        command "push6"
        command "push7"
        command "push8"
        command "push9"
        command "SendString"
       // command "StringButtonNumber"
        //command "StringDeviceName"
        //attribute "string"
       // command "open"
		//command "close"
        //attribute "greeting1","string"
	}

	tiles {
    standardTile("shield", "device.shield", width: 2, height: 2) {
			state "default", icon:"st.shields.shields.arduino", backgroundColor:"#ffffff"
		}
    standardTile("upstairsShowerSensor", "device.UpstairsShower", width: 1, height: 1) {
			state "dry", icon:"st.alarm.water.dry", backgroundColor:"#ffffff"
			state "wet", icon:"st.alarm.water.wet", backgroundColor:"#53a7c0"

		        
		}
        
        
    standardTile("vent1switch", "device.vent1switch", width: 1, height: 1, canChangeIcon: true) {
			state "on", action:"switch.off", icon:"st.vents.vent-open-text", backgroundColor:"#53a7c0"
			state "off", action:"switch.on", icon:"st.vents.vent-closed", backgroundColor:"#ffffff"
		}
    controlTile("vent1Slider", "device.level", "slider", height: 1, width: 2, inactiveLabel: false) {
			state "vent1level", action:"switch level.setLevel"
		}
    standardTile("button1", "device.button1", width: 1, height: 1, decoration: "flat") {
			state "default", label: "TV A", backgroundColor: "#ffffff", action: "push1"
		} 
 	standardTile("button2", "device.button2", width: 1, height: 1, decoration: "flat") {
			state "default", label: "TV B", backgroundColor: "#ffffff", action: "push2"
		}
    standardTile("button3", "device.button3", width: 1, height: 1, decoration: "flat") {
			state "default", label: "TV C", backgroundColor: "#ffffff", action: "push3"
		}
    standardTile("button4", "device.button4", width: 1, height: 1, decoration: "flat") {
			state "default", label: "TV D", backgroundColor: "#ffffff", action: "push4"
            }
    //standardTile("button5", "device.button", width: 1, height: 1, decoration: "flat") {
	//		state "default", label: "Adelka", backgroundColor: "#ffffff", action: "push5"
    //        }
    //standardTile("button5", "device.AdelkaLightButton", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
		//	state "on", label: "Adelka", action: "push5", icon: "st.switches.switch.on", backgroundColor: "#79b821"
		//	state "off", label: "Adelka", action: "push5", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
		//}
    standardTile("button5", "device.AdelkaLightButton1", width: 1, height: 1, canChangeIcon: true) {
		state "on", label: "Adelka", action:"push5", icon:"st.lights.philips.hue-single", backgroundColor:"#79b821", nextState:"turningOff"
		state "off", label: "Adelka", action:"push5", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
		state "turningOn", label: "ON", action:"push5", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOff"
		state "turningOff", label: "Off", action:"push5", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
       // state "motionActive", label:'motion', icon:"st.motion.motion.active", backgroundColor:"#53a7c0"
		//state "motionInactive", label:'no motion', icon:"st.motion.motion.inactive", backgroundColor:"#ffffff"
	}
    standardTile("button6", "device.AdelkaLightButton2", width: 1, height: 1, canChangeIcon: true) {
		state "on", label: "Bath", action:"push6", icon:"st.lights.philips.hue-single", backgroundColor:"#79b821", nextState:"turningOff"
		state "off", label: "Bath", action:"push6", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
		state "turningOn", label: "ON", action:"push6", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOff"
		state "turningOff", label: "OFF", action:"push6", icon:"st.lights.philips.hue-single", backgroundColor:"#ffffff", nextState:"turningOn"
	}
    standardTile("button7", "device.button7", width: 1, height: 1, decoration: "flat") {
			state "default", label: "7", backgroundColor: "#ffffff", action: "push7"
            }
    standardTile("button8", "device.button8", width: 1, height: 1, decoration: "flat") {
			state "default", label: "8", backgroundColor: "#ffffff", action: "push8"
            }
    standardTile("button9", "device.button9", width: 1, height: 1, decoration: "flat") {
			state "default", label: "9", backgroundColor: "#ffffff", action: "push9"
            }
    
		main "shield"
		details(["vent1switch","vent1Slider","upstairsShowerSensor","button1","button2","button3","button4","button5","button6","button7","button8","button9"])
	}
}

// Parse incoming device messages to generate events
def parse(String description){
	
  	def messageFromArduino = zigbee.parse(description)?.text
    
    log.debug "Message from Arduino: ${messageFromArduino}"
       	
       		if (messageFromArduino.contains("ping")){ 
            // log.debug "Ping"
            sendEvent(name: "ArduinoHomeControl", value: "ping", descriptionText: "ArduinoHomeControl - Ping", isStateChange: true)
    			
             }
           
        	if (messageFromArduino.contains(":")){ 
        	def messageFromArduinoParts = messageFromArduino.split( /:/ );
            sendEvent(name: messageFromArduinoParts[0], value: messageFromArduinoParts[1], descriptionText: "${messageFromArduinoParts[0]} is ${messageFromArduinoParts[1]}", isStateChange: true)
            //log.debug "Message from Arduino Part 1: ${messageFromArduinoParts[0]}"
            //log.debug "Message from Arduino Part 2: ${messageFromArduinoParts[1]}"
            } 
        
	
}
// Commands sent to the device
def push1() {	
	
    sendEvent(name: "button1", value: "pushed", descriptionText: "$device.displayName button 1 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "push1").format()
}
def push2() {	
    sendEvent(name: "button2", value: "pushed", descriptionText: "$device.displayName button 2 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "push2").format()
}
def push3() {
	sendEvent(name: "button3", value: "pushed", descriptionText: "$device.displayName button 3 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "push3").format()
}
def push4() {
	sendEvent(name: "button4", value: "pushed", descriptionText: "$device.displayName button 4 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "push4").format()
}
def push5() {
	//createEvent(name: "Adelka Light Button", value: "pushed", descriptionText: "${messageparts[0]} is ${messageparts[1]}")
    sendEvent(name: "button5", value: "pushed", descriptionText: "$device.displayName button 5 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "push5").format()
}
def push6() {
	sendEvent(name: "button6", value: "pushed", descriptionText: "$device.displayName button 6 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "push6").format()
}
def push7() {
	sendEvent(name: "button7", value: "pushed", descriptionText: "$device.displayName button 7 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "push7").format()
   // createEvent(name: "upstairsShowerState", value: "wet", descriptionText: "$device.displayName is wet")
}
def push8() {
	sendEvent(name: "button8", value: "pushed", descriptionText: "$device.displayName button 8 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "push8").format()
   
}
def push9() {
	sendEvent(name: "button9", value: "pushed", descriptionText: "$device.displayName button 9 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "push9").format()
}

def SendString(String messageFromApp) {
   
   // zigbee.smartShield(text: "$messageFromApp").format()
   
    
    ///*
   	
    //log.debug "Message from App: ${messageFromApp}"
   
        
        
        
        //if (messageFromApp.contains("button")){
          // def messageFromAppParts = messageFromApp.split( /:/ );
           	
         //   sendEvent(name: messageFromAppParts[0], value: messageFromAppParts[1], data: [buttonNumber: messageFromAppParts[2]],descriptionText: "${messageFromAppParts[0]} is ${messageFromAppParts[1]} ${messageFromAppParts[2]}", isStateChange: true)
           
         // }
      if (messageFromApp.contains(":")){ 
      
      def messageFromAppParts = messageFromApp.split( /:/ );
      sendEvent(name: messageFromAppParts[0], value: messageFromAppParts[1],descriptionText: "${messageFromAppParts[0]} is ${messageFromAppParts[1]}", isStateChange: true)
      zigbee.smartShield(text: messageFromApp).format() 
      //log.debug "Message from App: ${messageFromApp}"
       }    
           
   //        } 
   
   	//*/
   }


 
 
 


//def setLevel(value) {
    
   
    //log.debug "Slider value: ${value}%"
    //zigbee.smartShield(text: "$value").format()
   // } 
 