package com.dk.etl.extra.config;

import com.dk.etl.extra.convert.TranStepMetaConvert;
import com.dk.etl.extra.exception.InitException;
import com.google.common.collect.Lists;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassDef;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassMeta;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:52
 * @Version:1.0
 */

public class UserDefinedJavaClassMetaCfg extends BaseCfg implements TranStepMetaConvert {
    private String javaSource;
    private List<FieldInfo> fields;

    public UserDefinedJavaClassMetaCfg() {
    }

    public String getJavaSource() {
        return this.javaSource;
    }

    public void setJavaSource(String javaSource) {
        this.javaSource = javaSource;
    }

    public List<UserDefinedJavaClassMetaCfg.FieldInfo> getFields() {
        return this.fields;
    }

    public void setFields(List<UserDefinedJavaClassMetaCfg.FieldInfo> fields) {
        this.fields = fields;
    }

    public void init(Map<String, Object> params) throws InitException {
    }

    @Override
    public String getConfKey() {
        return "javacode";
    }

    @Override
    public StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> datasouces, StepMeta preste) {
        UserDefinedJavaClassMeta userDefinedJavaClassMeta = new UserDefinedJavaClassMeta();
        userDefinedJavaClassMeta.setDefault();
        if (this.getFields() != null) {
            userDefinedJavaClassMeta.replaceFields((List)this.getFields().stream().map((f) -> {
                return new org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassMeta.FieldInfo(f.getName(), f.getType(), f.getLength(), f.getPrecision());
            }).collect(Collectors.toList()));
        }

        userDefinedJavaClassMeta.replaceDefinitions(Lists.newArrayList(new UserDefinedJavaClassDef[]{new UserDefinedJavaClassDef(UserDefinedJavaClassDef.ClassType.TRANSFORM_CLASS, "Processor", this.getJavaSource())}));
        return userDefinedJavaClassMeta;
    }

    public static class FieldInfo implements Cloneable {
        private String name;
        private int type;
        private int length;
        private int precision;

        public FieldInfo(String name, int type, int length, int precision) {
            this.name = name;
            this.type = type;
            this.length = length;
            this.precision = precision;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getLength() {
            return this.length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getPrecision() {
            return this.precision;
        }

        public void setPrecision(int precision) {
            this.precision = precision;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
