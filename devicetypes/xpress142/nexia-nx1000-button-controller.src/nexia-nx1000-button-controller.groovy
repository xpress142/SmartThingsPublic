/**


 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

 metadata {

  definition (name: "Nexia NX1000 Button Controller", namespace: "Xpress142", author: "Xpress142") {
        capability "Actuator"
        capability "Button"
        capability "Configuration"
        
        command "push1"
  
  
 
  }
  tiles {
	standardTile("button", "device.button", width: 2, height: 2) {
      state "default", label: " ", icon: "st.unknown.zwave.remote-controller", backgroundColor: "#ffffff"
    }
    
     standardTile("button1", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "PUSH", backgroundColor: "#ffffff", action: "push1"
	} 
      
    standardTile("configure", "device.configure", inactiveLabel: false, decoration: "flat") {
      state "configure", label:'', action:"configuration.configure", icon:"st.secondary.configure"
    }
    main "button"
    details (["button", "configure","button1"])
  }
}

def parse(description) {
  log.debug "Description: ${description}"
 
  def cmd = zwave.parse(description)
 log.debug "CMD: ${cmd}"
  //log.debug "Button Pressed: ${cmd.sceneNumber}"
  //log.debug "Sequence Number: ${cmd.sequenceNumber}"
 
 // sendEvent(name: "button", value: "pushed", data: [buttonNumber: "${cmd.sceneNumber}"], descriptionText: "$device.displayName button ${cmd.sceneNumber} was pushed", isStateChange: true)
}
def configurationCmds() {
  //log.debug "Resetting Sensor Parameters to SmartThings Compatible Defaults"
  def commands = [

	//zwave.sceneControllerConfV1.sceneControllerConfSet(groupId:1, sceneId:1).format(),
	zwave.configurationV1.configurationSet(configurationValue: [3], parameterNumber: 2, size: 1).format(),
   	zwave.configurationV1.configurationSet(configurationValue: [3], parameterNumber: 3, size: 1).format()
    
   ]

}
def configure() {
  
  
  def cmd=configurationCmds()
    log.debug("Sending configuration: ${cmd}")
    return cmd
    }
    
    //0x00 = Central Scene 
    //0x01 = Scene Control Momentary 
    //0x02 = BASICSET Toggle 
    //0x03 = Scene Control/BASICSET toggle 
    //0x04 = Thermostat
    
def push1() {	
	
    log.debug "Button Pressed"
 
    zwave.screenmd(screenSettings: 0).format()
    
    
    //class physicalgraph.zwave.commands.screenmdv1.ScreenMdReport 
	//{
	//Short	charPresentation
	//Boolean	moreData
	//Short	screenSettings
	//List<Short>	payload
 
	//String format()
	//}
}