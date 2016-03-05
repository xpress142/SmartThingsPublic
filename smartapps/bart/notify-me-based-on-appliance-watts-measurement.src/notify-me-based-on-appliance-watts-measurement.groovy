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
    name: "Notify Me based on Appliance Watts Measurement",
    namespace: "Bart",
    author: "Bart",
    description: "Get notified when appliance turns ON or OFF based on Watts Measuremen",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/FunAndSocial/App-HotTubTuner.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/FunAndSocial/App-HotTubTuner%402x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/FunAndSocial/App-HotTubTuner%402x.png"
)

preferences {
	section("Select Meter Device"){
		input(name: "meter", type: "capability.energyMeter", title: "When This Power Meter...", required: true, multiple: false, description: null)
    }    
    section("When Above"){    
        input(name: "aboveThreshold", type: "number", title: "Reports Above...", required: true, description: "in either watts or kw.")
        input(name: "aboveMessage", type: "text", title: "Message When Above...", required: true, description: "Ended")
    }
    section("When Below"){  
        input(name: "belowThreshold", type: "number", title: "Or Reports Below...", required: true, description: "in either watts or kw.")
        input(name: "belowMessage", type: "text", title: "Message When Below...", required: true, description: "Ended")      
	}
    section {
        input("recipients", "contact", title: "Send notifications to") {
            input(name: "sms1", type: "phone", title: "Send A Text To", description: null, required: false)
            input(name: "sms2", type: "phone", title: "Send A Text To", description: null, required: false)
            input(name: "pushNotification", type: "bool", title: "Send a push notification", description: null, defaultValue: true)
        }
   
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(meter, "power", meterHandler)
}

def meterHandler(evt) {

    def meterValue = evt.value as double

    if (!atomicState.lastValue) {
    	atomicState.lastValue = meterValue
    }

    def lastValue = atomicState.lastValue as double
    atomicState.lastValue = meterValue

    def aboveThresholdValue = aboveThreshold as int
    if (meterValue > aboveThresholdValue) {
    	if (lastValue < aboveThresholdValue) { // only send notifications when crossing the threshold
		    def msg = "${meter} ${aboveMessage}"
    	    sendMessage(msg)
        } else {
//        	log.debug "not sending notification for ${evt.description} because the threshold (${aboveThreshold}) has already been crossed"
        }
    }


    def belowThresholdValue = belowThreshold as int
    if (meterValue < belowThresholdValue) {
    	if (lastValue > belowThresholdValue) { // only send notifications when crossing the threshold
		    def msg = "${meter} ${belowMessage}"
    	    sendMessage(msg)
        } else {
//        	log.debug "not sending notification for ${evt.description} because the threshold (${belowThreshold}) has already been crossed"
        }
    }
}

def sendMessage(msg) {
    if (location.contactBookEnabled) {
        sendNotificationToContacts(msg, recipients)
    }
    else {
        if (sms) {
            sendSms(sms1, msg)
            sendSms(sms2, msg)
        }
        if (pushNotification) {
            sendPush(msg)
        }
        
     }
     
}
