/**
 *  Energy Saver
 *
 *  Copyright 2014 SmartThings
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
definition(
    name: "PS4 Lights OFF & Back to Original State ",
    namespace: "Bart",
    author: "Bart",
		description: "Turn things off if you're using too much energy",
    category: "Green Living",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet@2x.png"
)

preferences {
	section {
		input(name: "meter", type: "capability.energyMeter", title: "When This Power Meter...", required: true, multiple: false, description: null)
        input(name: "threshold", type: "number", title: "Reports Above...", required: true, description: "in either watts or kw.")
	}
    section {
    	input(name: "lights", type: "capability.switchLevel", title: "Lights (up to 6)", required: true, multiple: true, max: 6, description: null)
    }
    
    
    
    
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug " 4 Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(meter, "power", meterHandler)
    subscribe(lights, "switch.on", switchesOnHandler)
    subscribe(lights, "switch", switchesHandler)
    state.TriggerState = "ON"
    state.SwitchChange = "NO"
    
}
def meterHandler(evt) {
	//log.debug "will send the on() command to ${lights.size()} switches"
    
    //def currSwitches = lights.currentSwitch

   // def onSwitches = currSwitches.findAll 
    

    //log.debug "${onSwitches.size()} out of ${lights.size()} switches are on"
    
    //log.debug "${state.TriggerState}"
    def meterValue = evt.value as double
    def thresholdValue = threshold as int
    if (state.TriggerState == "ON") {
    	
    	if (meterValue > thresholdValue) {
        	//state.SwitchOn = "NO"
	   		//log.debug "${meter} reported energy consumption above ${threshold}. Turning of switches."
    		//unsubscribe(lights)
        	state.lightsCurrentState = lights*.currentValue("level")
            lights*.off()
        
            state.TriggerState = "OFF"
            log.debug "Trigger State: ${state.TriggerState}"
            
           
            state.SwitchOn = "NO"
            log.debug "Switch ON: ${state.SwitchOn}"
            
            
            
    	}
    }    
	
    	
    if(meterValue < thresholdValue) {
   		
        	if (state.SwitchOn == "NO") {
    		
       //def eachlight = state.lightsCurrentState
       //log.debug "${lights.size()}"
      // log.debug "${lights.findAll()}"
      // "${eachlight.each{-> lights.findAll()}}"
      
      //state.lightsCurrentState.each{num -> lights*.setLevel num}
       
       
       //lights*.setLevel(state*.lightsCurrentState)
       
       
           if(lights.size() == 1) {
           lights[0].setLevel(state.lightsCurrentState[0])
           }
           else if(lights.size() == 2) {
           lights[0].setLevel(state.lightsCurrentState[0])
           lights[1].setLevel(state.lightsCurrentState[1])
           }	
           else if(lights.size() == 3) {
           lights[0].setLevel(state.lightsCurrentState[0])
           lights[1].setLevel(state.lightsCurrentState[1])
           lights[2].setLevel(state.lightsCurrentState[2])
           state.lightsCurrentState = [0, 0, 0]
           }		
           else if(lights.size() == 4) {
           lights[0].setLevel(state.lightsCurrentState[0])
           lights[1].setLevel(state.lightsCurrentState[1])
           lights[2].setLevel(state.lightsCurrentState[2])
           lights[3].setLevel(state.lightsCurrentState[3])
           }	
           else if(lights.size() == 5) {
           lights[0].setLevel(state.lightsCurrentState[0])
           lights[1].setLevel(state.lightsCurrentState[1])
           lights[2].setLevel(state.lightsCurrentState[2])
           lights[3].setLevel(state.lightsCurrentState[3])
           lights[4].setLevel(state.lightsCurrentState[4])
           }	
           else if(lights.size() == 6) {
           lights[0].setLevel(state.lightsCurrentState[0])
           lights[1].setLevel(state.lightsCurrentState[1])
           lights[2].setLevel(state.lightsCurrentState[2])
           lights[3].setLevel(state.lightsCurrentState[3])
           lights[4].setLevel(state.lightsCurrentState[4])
           lights[5].setLevel(state.lightsCurrentState[5])
           }	
       	   log.debug "${state.lightsCurrentState}"
       
      
      
    	}
        state.TriggerState = "ON"
        log.debug "Trigger State: ${state.TriggerState}"
     }   
}


def switchesOnHandler(evt) {
	
    state.SwitchOn = "Yes"
   	//state.lightsCurrentState = lights*.currentValue("level")
    log.debug "Switch ON: ${state.SwitchOn}"
    
    
    }
    
    
def switchesHandler(evt) {
	
    
   //	state.lightsCurrentState = lights*.currentValue("level")

    
    
    }
    