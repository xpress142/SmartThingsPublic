definition(
    name: "1 Button Change Dim Level",
    namespace: "Bart",
    author: "Bart",
    description: "Increase / decrease light's brightness using 1 button",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")



preferences {

section("Button Device") {
		input(name: "buttonDevice", type: "capability.button", title: "Button", multiple: false, required: true)		
        }       
section("Dimmer") {
		input(name: "dimmer1", type: "capability.switchLevel", title: "Dimmer", multiple: false, required: false)
        input(name: "dimmer1IncreaseLightButton", type: "enum", options: [[button1:"Button 1"],[button2:"Button 2"],[button3:"Button 3"],[button4:"Button 4"],[button5:"Button 5"],[button6:"Button 6"],[button7:"Button 7"],[button8:"Button 8"],[button9:"Button 9"]], title: "Increase Light Button", required: true, multiple: false)
       	input(name: "dimmer1Level1", type: "enum", options: [[0:"Off"],[5:"5%"],[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[99:"100%"]], title: "First Level", required: true, multiple: false)
      	input(name: "dimmer1Level2", type: "enum", options: [[0:"Off"],[5:"5%"],[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[99:"100%"]], title: "Second Level", required: true, multiple: false)
      	input(name: "dimmer1Level3", type: "enum", options: [[0:"Off"],[5:"5%"],[10:"10%"],[20:"20%"],[30:"30%"],[40:"40%"],[50:"50%"],[60:"60%"],[70:"70%"],[80:"80%"],[90:"90%"],[99:"100%"]], title: "Third Level", required: true, multiple: false)
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
	subscribe(buttonDevice, dimmer1IncreaseLightButton, buttonEvent)
    
}

def buttonEvent(evt){
		

	//if (evt.data.contains(dimmer1IncreaseLightButton)) { 

		if (state.currentDimmer1Level == dimmer1Level1) {
        	dimmer1.setLevel(dimmer1Level2)
        	state.currentDimmer1Level = dimmer1Level2      		
       		}
		else if (state.currentDimmer1Level == dimmer1Level2) {
       		dimmer1.setLevel(dimmer1Level3)	
            state.currentDimmer1Level = dimmer1Level3   	
       		}
		else if (state.currentDimmer1Level == dimmer1Level3) {
       		dimmer1.setLevel(0)	
            state.currentDimmer1Level = 0
        	}
        else {    	
            dimmer1.setLevel(dimmer1Level1)	
            state.currentDimmer1Level = dimmer1Level1   
       		}
			//log.debug state.currentDimmer1Level
	//}
}