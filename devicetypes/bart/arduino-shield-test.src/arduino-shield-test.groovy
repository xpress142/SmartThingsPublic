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
	definition (name: "Arduino Shield - TEST", namespace: "Bart", author: "Bart") {
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
        command "SendString"
        command "adelkaVentSliderSetLevel"
        command "adelkaVentOpen" 
        command "adelkaVentClose"

       // command "StringButtonNumber"
        //command "StringDeviceName"
        //attribute "string"
       // command "open"
		//command "close"
        //attribute "greeting1","string"
	}

	//tiles {
	tiles(scale: 2) {
		multiAttributeTile(name:"vent1switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
			tileAttribute ("device.AdelkaVentSlider", key: "SLIDER_CONTROL") {
				attributeState "level", action:"adelkaVentSliderSetLevel"
			}
            tileAttribute ("device.AdelkaVentSlider", key: "PRIMARY_CONTROL") {
				attributeState "99",/*label:'${name}',*/action:"adelkaVentClose", icon:"st.vents.vent-open-text", backgroundColor:"#53a7c0", nextState:"Closing"
				attributeState "0",/*label:'${name}',*/ action:"adelkaVentOpen", icon:"st.vents.vent-closed", backgroundColor:"#ffffff", nextState:"Opening"
				attributeState "Opening",/* label:'${name}',*/ action:"adelkaVentClose", icon:"st.vents.vent-open-text", backgroundColor:"##44b621", nextState:"Closing"
				attributeState "Closing",/* label:'${name}',*/ action:"adelkaVentOpen", icon:"st.vents.vent-closed", backgroundColor:"##44b621", nextState:"Opening"
			}
			
            tileAttribute("device.AdelkaVentSlider", key: "SECONDARY_CONTROL") {
   				attributeState("level", label:'${currentValue}%', unit:"%")
  }
		}
    
    standardTile("shield", "device.shield", width: 2, height: 2) {
			state "default", icon:"st.shields.shields.arduino", backgroundColor:"#ffffff"
		}   
    //standardTile("vent1switch", "device.AdelkaVentSlider", width: 1, height: 1, canChangeIcon: true) {
		//	state "99", action:"adelkaVentClose", icon:"st.vents.vent-open-text", backgroundColor:"#53a7c0"
		//	state "0", action:"adelkaVentOpen", icon:"st.vents.vent-closed", backgroundColor:"#ffffff"
		//}
	
    //controlTile("adelkaVentSlider", "device.AdelkaVentSlider", "slider", height: 1, width: 2, inactiveLabel: false) {
		//	state "level", action:"adelkaVentSliderSetLevel"
		//}
    //valueTile("adelkaVentState", "device.AdelkaVentSlider", decoration: "flat", wordWrap: false, width: 1, height: 1) {
       // state("level", label:'${currentValue}%', unit:"", backgroundColor:"#ffffff")
    	//}
    standardTile("button1", "device.button", width: 2, height: 2, decoration: "flat") {
			state "default", label: "1", backgroundColor: "#ffffff", action: "push1"
		} 
 	standardTile("button2", "device.button", width: 2, height: 2, decoration: "flat") {
			state "default", label: "2", backgroundColor: "#ffffff", action: "push2"
		}
    standardTile("button3", "device.button", width: 2, height: 2, decoration: "flat") {
			state "default", label: "3", backgroundColor: "#ffffff", action: "push3"
		}
 	

  

		main "shield"
		details(["vent1switch",/*"adelkaVentSlider","adelkaVentState"*/,"button1","button2","button3"])
	}
}

//def setLevel(value) {
def adelkaVentSliderSetLevel(value){    
    //log.debug "Name: ${duration}"
    //log.debug "adelkaVentSlider:${value}%"
    zigbee.smartShield(text: "AdelkaVentSlider:$value:").format() //Use ":" at the end when sending to Arduino
    }
def adelkaVentOpen(){
	zigbee.smartShield(text: "AdelkaVentSlider:99:").format() //Use ":" at the end when sending to Arduino
	}
def adelkaVentClose(){
   	zigbee.smartShield(text: "AdelkaVentSlider:0:").format() //Use ":" at the end when sending to Arduino
   	}
   
   
// Parse incoming device messages to generate events
def parse(String description){
	
    
    def message = zigbee.parse(description)?.text
	//log.debug "Value: ${value}"
    //sendEvent(name: "button", value: "pushed", data: [buttonNumber: value], descriptionText: "$device.displayName button 1 was pushed", isStateChange: true)
    if (message.contains(":")){
            def messageparts = message.split( /:/ );
           	log.debug "Value: ${messageparts[1]}"
            sendEvent(name: messageparts[0], value: messageparts[1],descriptionText: "${messageparts[0]} is ${messageparts[1]}", isStateChange: true)
           
           }
}
// Commands sent to the device
def push1() {	
	
    //sendEvent(name: "button", value: "pushed", data: [buttonNumber: "1"], descriptionText: "$device.displayName button 1 was pushed", isStateChange: true)
    zigbee.smartShield(text: "push1").format()
}
def push2() {	
    //sendEvent(name: "button", value: "pushed", data: [buttonNumber: "2"], descriptionText: "$device.displayName button 2 was pushed", isStateChange: true)
    zigbee.smartShield(text: "push2").format()
}
def push3() {
	//sendEvent(name: "button", value: "pushed", data: [buttonNumber: "3"], descriptionText: "$device.displayName button 3 was pushed", isStateChange: true)
    zigbee.smartShield(text: "push3").format()
}

//def SendString(String output) {
    
    //zigbee.smartShield(text: "$output").format()
    //log.debug "Device: ${output}" 

