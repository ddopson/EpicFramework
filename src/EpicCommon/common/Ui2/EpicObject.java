package com.epic.framework.common.Ui2;


public abstract class EpicObject {
	public EpicClass type;
	protected void initialize() { }
	
	public static final int FIELD_REQUIRED = 0x3;
	public static final int FIELD_NULLABLE = 0x2;
	public static final int FIELD_OPTIONAL = 0x0;
	public static final int FIELDMASK_REQUIRED = 0x1;
	public static final int FIELDMASK_NULLABLE = 0x1;
}
