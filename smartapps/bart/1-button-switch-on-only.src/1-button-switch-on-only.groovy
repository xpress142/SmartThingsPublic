definition(
    name: "1 Button Switch ON Only",
    namespace: "Bart",
    author: "Bart",
    description: "ON light using 1 button",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")



preferences {

section("Button Device") {
		input(name: "buttonDevice", type: "capability.button", title: "Button Device", multiple: false, required: true)		
        input(name: "OnButton", type: "enum", options: [[button1:"Button 1"],[button2:"Button 2"],[button3:"Button 3"],[button4:"Button 4"],[button5:"Button 5"],[button6:"Button 6"],[button7:"Button 7"],[button8:"Button 8"],[button9:"Button 9"]], title: "Button Number", required: true, multiple: false)
        }       
section("Dimmer") {
		input(name: "switches", type: "capability.switch", title: "Switch", multiple: true, required: false)
        
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
	subscribe(buttonDevice, OnButton, buttonEvent)
    
}

def buttonEvent(evt){
		
        

	//if (evt.data.contains(OnOffButton)) { 
    
  
		switches.on()
        
        		
       //	}
		
	
}