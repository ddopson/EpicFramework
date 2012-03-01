CLASSES="
  EpicAssertionFailedException
  EpicFrameworkException
  EpicInvalidArgumentException
  EpicJSONException
  EpicObjectInflationException
  EpicMissingImageException
  EpicMissingSoundException
  EpicMissingFileException
  EpicNoSuchElementException
  EpicNativeMethodMissingImplementation
  EpicNotImplementedException
  EpicNullPointerException
  EpicNotSupportedException
  EpicStringFormatException
  EpicSerializationException
  EpicUnexpectedDataException
  EpicUnhandledCaseException
"

for CLASSNAME in $CLASSES; do
echo 'package com.epic.framework.common.util.exceptions;

public class __CLASSNAME__ extends EpicRuntimeException {
	private static final long serialVersionUID = 0L;
	public __CLASSNAME__() {
		super("__CLASSNAME__", "no message", null);
	}
	public __CLASSNAME__(Throwable cause) {
		super("__CLASSNAME__", "no message", cause);
	}
	public __CLASSNAME__(String msg) {
		super("__CLASSNAME__", msg, null);
	}
	public __CLASSNAME__(String msg, Throwable cause) {
		super("__CLASSNAME__", msg, null);
	}
}' | perl -pe "s/__CLASSNAME__/$CLASSNAME/g" > $CLASSNAME.java

done
