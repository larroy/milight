package com.larroy.milight

object CommandCode extends Enumeration {
  type Enum = Value
  	/**
	 * The command code for "RGBW COLOR LED ALL OFF".
	 */
	var ALL_OFF = Value(0x41)

	/**
	 * The command code for "GROUP 1 ALL OFF".
	 */
  val GROUP_1_OFF = Value(0x46)

	/**
	 * The command code for "GROUP 2 ALL OFF".
	 */
	val GROUP_2_OFF = Value(0x48)
	/**
	 * The command code for "GROUP 3 ALL OFF".
	 */
	val GROUP_3_OFF = Value(0x4A)
	/**
	 * The command code for "GROUP 4 ALL OFF".
	 */
	val GROUP_4_OFF = Value(0x4C)
	/**
	 * The command code for "RGBW COLOR LED ALL ON".
	 */
	val ALL_ON = Value(0x42)

	/**
	 * The command code for "GROUP 1 ALL ON".
	 */
	val GROUP_1_ON = Value(0x45)

	/**
	 * The command code for "GROUP 2 ALL ON".
	 */
	val GROUP_2_ON = Value(0x47)

	/**
	 * The command code for "GROUP 3 ALL ON".
	 */
	val GROUP_3_ON = Value(0x49)

	/**
	 * The command code for "GROUP 4 ALL ON".
	 */
	val GROUP_4_ON = Value(0x4B)

	/**
	 * The command code for "SET COLOR TO WHITE (GROUP ALL)". Send an "ON"
	 * command 100ms before.
	 */
	val ALL_WHITE = Value(0xC2)

	/**
	 * The command code for "SET COLOR TO WHITE (GROUP 1)". Send an "ON" command
	 * 100ms before.
	 */
	val GROUP_1_WHITE = Value(0xC5)

	/**
	 * The command code for "SET COLOR TO WHITE (GROUP 2)". Send an "ON" command
	 * 100ms before.
	 */
	val GROUP_2_WHITE = Value(0xC7)

	/**
	 * The command code for "SET COLOR TO WHITE (GROUP 3)". Send an "ON" command
	 * 100ms before.
	 */
	val GROUP_3_WHITE = Value(0xC9)

	/**
	 * The command code for "SET COLOR TO WHITE (GROUP 4)". Send an "ON" command
	 * 100ms before.
	 */
	val GROUP_4_WHITE = Value(0xCB)

	/**
	 * The command code for "DISCO MODE".
	 */
	val DISCO = Value(0x4D)

	/**
	 * The command code for "DISCO SPEED FASTER".
	 */
	val DISCO_FASTER = Value(0x44)

	/**
	 * The command code for "DISCO SPEED SLOWER".
	 */
	val DISCO_SLOWER = Value(0x43)

	/**
	 * The command code for "COLOR SETTING" (part of a two-byte * command).
	 */
	val COLOR = Value(0x40)

	/**
	 * The maximum color value, starting at 0.
	 */
	val MAX_COLOR = Value(0xFF)

	/**
	 * The command code for "DIRECT BRIGHTNESS SETTING" (part of a two-byte * command).
	 */
	val BRIGHTNESS = Value(0x4E)

	/**
	 * The maximum brightness value, starting at 0.
	 */
	val MAX_BRIGHTNESS = Value(0x3B)

	/**
		* NIGHTMODE commands, send "OFF" before. Ex. 0x41 -> 0xC1
		*/

	val NIGHTMODE_ALL = Value(0xC1)
	val GROUP_1_NIGHTMODE = Value(0xC6)
	val GROUP_2_NIGHTMODE = Value(0xC8)
	val GROUP_3_NIGHTMODE = Value(0xCA)
	val GROUP_4_NIGHTMODE = Value(0xCC)
}

