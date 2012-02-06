package com.epic.framework.common.Ui2;

import com.epic.framework.common.util.exceptions.EpicObjectInflationException;

public class EpicInflationContext {
	public EpicObjectInflationException inflateAbstractClass(String classname) {
		throw new EpicObjectInflationException("Can't inflate abstract class " + classname);
	}
}
