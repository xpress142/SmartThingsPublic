definition(
    name: "1 Button Toggle Switch ON / OFF - Adelka Buttons",
    namespace: "Bart",
    author: "Bart",
    description: "ON /OFF light using 1 button - Adelka Room & Bathroom with LED status & Motion",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")



preferences {

section("Arduino Device") {
		input(name: "arduinoDevice", type: "capability.button", title: "Arduino Device", multiple: false, required: true)		
        
        }       
section("1st Button") {
		input(name: "arduinoButton1", type: "enum", options: [[button1:"Button 1"],[button2:"Button 2"],[button3:"Button 3"],[button4:"Button 4"],[button5:"Button 5"],[button6:"Button 6"],[button7:"Button 7"],[button8:"Button 8"],[button9:"Button 9"]], title: "Arduino Button", required: true, multiple: false)       
		input(name: "button1Switches", type: "capability.switch", title: "Switches", multiple: true, required: true)
        input(name: "button1MotionSensor", type: "capability.motionSensor", title: "Motion Sensor", multiple: false, required: false)
    	}
section("2nd Button") { 
		input(name: "arduinoButton2", type: "enum", options: [[button1:"Button 1"],[button2:"Button 2"],[button3:"Button 3"],[button4:"Button 4"],[button5:"Button 5"],[button6:"Button 6"],[button7:"Button 7"],[button8:"Button 8"],[button9:"Button 9"]], title: "Arduino Button", required: false, multiple: false) 
        input(name: "button2Switches", type: "capability.switch", title: "Switches", multiple: true, required: false)
        input(name: "button2MotionSensor", type: "capability.motionSensor", title: "Motion Sensor", multiple: false, required: false)
    	}
      
      
}


def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(arduinoDevice, "ArduinoHomeControl",  messageFromArduinoHomeControl)
	subscribe(arduinoDevice, arduinoButton1, button1Event)
    subscribe(arduinoDevice, arduinoButton2, button2Event)
    subscribe(button1Switches, "switch", button1SwitchEvent)
    subscribe(button1MotionSensor, "motion", button1MotionEvent)
    subscribe(button2Switches, "switch", button2SwitchEvent)
    subscribe(button2MotionSensor, "motion", button2MotionEvent)
    //buttonDevice.StringButtonNumber("$OnOffButton")
    //buttonDevice.StringDeviceName ("${adelkaSwitches[0]}") 
    //log.debug "${OnOffButton},${adelkaSwitches}" 

}     

def button1Event(evt){
	//log.debug "hi"	
       

	//if (evt.name.contains(arduinoButton1)) { 
    
    	//log.debug switches*.currentValue///('switch')
		//switches.on()
	
    	if (button1Switches.currentValue('switch').contains('on')) {
		button1Switches.off()
        arduinoDevice.SendString("AdelkaLightButton1:off")
		}
		else if (button1Switches.currentValue('switch').contains('off')) {
		button1Switches.on()
        arduinoDevice.SendString("AdelkaLightButton1:on")        		
       	}
		
	}
    
def button2Event(evt){   

		//if (evt.data.contains(arduinoButton2)) { 
    	//log.debug switches*.currentValue///('switch')
		//switches.on()
	
    	if (button2Switches.currentValue('switch').contains('on')) {
		button2Switches.off()
        arduinoDevice.SendString("AdelkaLightButton2:off")
		}
		else if (button2Switches.currentValue('switch').contains('off')) {
		button2Switches.on()
        arduinoDevice.SendString("AdelkaLightButton2:on")        		
       	}
		
	}


def button1SwitchEvent(evt){
         if (button1Switches.currentValue('switch').contains('on')) {

                //log.debug "${evt.value}" 
         arduinoDevice.SendString("AdelkaLightButton1:on") //Device 1 ON
              // buttonDevice.SendString()
         }
         else if (button1Switches.currentValue('switch').contains('off')) {
         arduinoDevice.SendString("AdelkaLightButton1:off") //Device 1 OFF
        }
}

def button2SwitchEvent(evt){
         if (button2Switches.currentValue('switch').contains('on')) {

                //log.debug "${evt.value}" 
         arduinoDevice.SendString("AdelkaLightButton2:on") //Device 1 ON
              // buttonDevice.SendString()
         }
         else if (button2Switches.currentValue('switch').contains('off')) {
         arduinoDevice.SendString("AdelkaLightButton2:off") //Device 1 OFF
        }
}


def button1MotionEvent(evt){
		//log.debug evt.value
		if("active" == evt.value) {
    	arduinoDevice.SendString("AdelkaLightButton1:motionActive") 
		}
        else if("inactive" == evt.value) {
    	arduinoDevice.SendString("AdelkaLightButton1:motionInactive") 
		}
    }   
    
def button2MotionEvent(evt){
		//log.debug evt.value
		if("active" == evt.value) {
    	arduinoDevice.SendString("AdelkaLightButton1:motionActive") 
		}
        else if("inactive" == evt.value) {
    	arduinoDevice.SendString("AdelkaLightButton1:motionInactive") 
		}
    }    
    
def messageFromArduinoHomeControl(evt){

	if("ping" == evt.value) {
    
    		log.debug button2Switches.currentValue('switch')
    	 if (button1Switches.currentValue('switch').contains('on')) {
         arduinoDevice.SendString("AdelkaLightButton1:on")
         }
         if (button1Switches.currentValue('switch').contains('off')) {
         arduinoDevice.SendString("AdelkaLightButton1:off")
         }
         if (button2Switches.currentValue('switch').contains('on')) {
         arduinoDevice.SendString("AdelkaLightButton2:on")        		
       	 }
         if (button2Switches.currentValue('switch').contains('off')) {
         arduinoDevice.SendString("AdelkaLightButton2:off")        		
       	 }
         
     }
}   