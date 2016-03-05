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
	definition (name: "Shield - Press 4 Buttons  - Bart", namespace: "Bart", author: "Bart") {
		capability "Actuator"
		capability "Button"
		capability "Sensor"
        //capability "refresh"
        //capability "string"
        
        command "pushA"
        command "pushB"
        command "pushC"
        command "pushD"
        
        //attribute "string"
	}

	// Simulator metadata
	simulator {
		status "on":  "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6E"
		status "off": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6666"

		// reply messages
		reply "raw 0x0 { 00 00 0a 0a 6f 6e }": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6E"
		reply "raw 0x0 { 00 00 0a 0a 6f 66 66 }": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A6F6666"
	}

	// UI tile definitions
	tiles {
	standardTile("buttonA", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "A", backgroundColor: "#ffffff", action: "pushA"
		} 
 	standardTile("buttonB", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "B", backgroundColor: "#ffffff", action: "pushB"
		}
    standardTile("buttonC", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "C", backgroundColor: "#ffffff", action: "pushC"
		}
    standardTile("buttonD", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "D", backgroundColor: "#ffffff", action: "pushD"
		} 
		main "buttonA"
		details(["buttonA","buttonB","buttonC","buttonD","message"])
	}
}

// Parse incoming device messages to generate events
def parse(String description){
	//log.debug “description: $description
    def value = zigbee.parse(description)?.text
	sendEvent(name: "button", value: "pushed", data: [buttonNumber: value], descriptionText: "$device.displayName button 1 was pushed", isStateChange: true)
    //def name = value in ["pushA","pushB"] ? "" : null
	//def result = createEvent(name: "pushA", value: value)
	//log.debug result?.descriptionText
  
	//return result

}
// Commands sent to the device
def pushA() {
	//log.debug "A"
    sendEvent(name: "button", value: "pushed", data: [buttonNumber: "1"], descriptionText: "$device.displayName button 1 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "pushA").format()
}

def pushB() {
	
    sendEvent(name: "button", value: "pushed", data: [buttonNumber: "2"], descriptionText: "$device.displayName button 2 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "pushB").format()

}

def pushC() {
	sendEvent(name: "button", value: "pushed", data: [buttonNumber: "3"], descriptionText: "$device.displayName button 3 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "pushC").format()

}

def pushD() {
	sendEvent(name: "button", value: "pushed", data: [buttonNumber: "4"], descriptionText: "$device.displayName button 4 was pushed", isStateChange: true)
    //zigbee.smartShield(text: "pushD").format()
}