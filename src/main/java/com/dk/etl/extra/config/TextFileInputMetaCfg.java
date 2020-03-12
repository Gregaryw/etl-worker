package com.dk.etl.extra.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dk.etl.extra.convert.TranStepMetaConvert;
import com.dk.etl.extra.exception.InitException;
import com.dk.etl.util.StringUtils;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.fileinput.text.TextFileInputMeta;

import java.util.Map;
import java.util.stream.IntStream;

/**
 * @Author: HarlanW
 * @Date: 2020/1/10 10:47
 * @Version:1.0
 */

public class TextFileInputMetaCfg extends BaseCfg implements TranStepMetaConvert {
    private String separator;
    private String encoding;
    private boolean hasHeader;
    private String[] fileName;
    private String[] fileMask;
    private TextFileInputMetaCfg.FileInputField[] fileInputFields;

    public TextFileInputMetaCfg() {
    }

    public String getSeparator() {
        return this.separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isHasHeader() {
        return this.hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public String[] getFileName() {
        return this.fileName;
    }

    public void setFileName(String[] fileName) {
        this.fileName = fileName;
    }

    public String[] getFileMask() {
        return this.fileMask;
    }

    public void setFileMask(String[] fileMask) {
        this.fileMask = fileMask;
    }

    public TextFileInputMetaCfg.FileInputField[] getFileInputFields() {
        return this.fileInputFields;
    }

    public void setFileInputFields(TextFileInputMetaCfg.FileInputField[] fileInputFields) {
        this.fileInputFields = fileInputFields;
    }

    public void init(Map<String, Object> params) throws InitException {
        JSONArray filePath = (JSONArray)params.get("filePath");
        this.fileName = (String[])filePath.stream().toArray((x$0) -> {
            return new String[x$0];
        });
        this.hasHeader = Boolean.valueOf(params.get("hasHeader").toString());
        this.encoding = params.get("encoding").toString();
        this.separator = params.get("separator").toString();
        JSONArray inputField = (JSONArray)params.get("inputField");
        this.fileInputFields = (TextFileInputMetaCfg.FileInputField[])inputField.stream().map((obj) -> {
            JSONObject json = (JSONObject)obj;
            TextFileInputMetaCfg.FileInputField field = new TextFileInputMetaCfg.FileInputField();
            field.setName(json.getString("name"));
            field.setType(json.getString("type"));
            field.setFormat(json.getString("format"));
            return field;
        }).toArray((x$0) -> {
            return new TextFileInputMetaCfg.FileInputField[x$0];
        });
        if (this.fileName == null || StringUtils.isEmpty(this.encoding) || StringUtils.isEmpty(this.separator) || this.fileInputFields == null) {
            throw new InitException("文件输入配置参数缺失");
        }
    }

    public String getConfKey() {
        return "fileinput";
    }

    public StepMetaInterface toStepMetaInterface(Map<String, DatabaseMeta> datasouces, StepMeta preste) {
        this.fileMask = new String[this.fileName.length];
        IntStream.range(0, this.fileMask.length).forEach((i) -> {
            this.fileMask[i] = ".*";
        });
        String[] fileRequired = new String[this.fileName.length];
        IntStream.range(0, this.fileMask.length).forEach((i) -> {
            fileRequired[i] = "N";
        });
        TextFileInputMeta textFileInputMeta = new TextFileInputMeta();
        textFileInputMeta.setDefault();
        textFileInputMeta.setAcceptingStep(preste);
        textFileInputMeta.content.separator = this.getSeparator();
        textFileInputMeta.content.encoding = this.getEncoding();
        textFileInputMeta.content.header = this.isHasHeader();
        textFileInputMeta.inputFiles.fileName = this.fileName;
        textFileInputMeta.inputFiles.fileMask = this.fileMask;
        textFileInputMeta.inputFiles.excludeFileMask = new String[this.fileName.length];
        textFileInputMeta.inputFiles.fileRequired = fileRequired;
        textFileInputMeta.inputFiles.includeSubFolders = fileRequired;
//        if (this.fileInputFields != null) {
//            BaseFileInputField[] baseFileInputFields = new BaseFileInputField[this.fileInputFields.length];
//            IntStream.range(0, this.fileInputFields.length).forEach((i) -> {
//                BaseFileInputField field = new BaseFileInputField();
//                field.setName(this.fileInputFields[i].name);
//                field.setType(this.fileInputFields[i].type);
//                field.setFormat(this.fileInputFields[i].format);
//                field.setCurrencySymbol(this.fileInputFields[i].currency);
//                field.setDecimalSymbol(this.fileInputFields[i].decimal);
//                field.setGroupSymbol(this.fileInputFields[i].group);
//                field.setNullString(this.fileInputFields[i].nullif);
//                field.setIfNullValue(this.fileInputFields[i].ifnull);
//                field.setPosition(this.fileInputFields[i].position == null ? -1 : this.fileInputFields[i].position);
//                field.setPrecision(this.fileInputFields[i].precision == null ? -1 : this.fileInputFields[i].precision);
//                field.setTrimType(this.fileInputFields[i].trimType);
//                field.setRepeated(this.fileInputFields[i].repeat == null ? false : this.fileInputFields[i].repeat);
//                baseFileInputFields[i] = field;
//            });
//            textFileInputMeta.inputFiles.inputFields = baseFileInputFields;
//        }

        return textFileInputMeta;
    }

    class FileInputField {
        private String name;
        private String type;
        private String format;
        private String currency;
        private String decimal;
        private String group;
        private String nullif;
        private String ifnull;
        private Integer position;
        private Integer length;
        private Integer precision;
        private String trimType;
        private Boolean repeat;

        FileInputField() {
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFormat() {
            return this.format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getCurrency() {
            return this.currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getDecimal() {
            return this.decimal;
        }

        public void setDecimal(String decimal) {
            this.decimal = decimal;
        }

        public String getGroup() {
            return this.group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getNullif() {
            return this.nullif;
        }

        public void setNullif(String nullif) {
            this.nullif = nullif;
        }

        public String getIfnull() {
            return this.ifnull;
        }

        public void setIfnull(String ifnull) {
            this.ifnull = ifnull;
        }

        public Integer getPosition() {
            return this.position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public Integer getLength() {
            return this.length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public Integer getPrecision() {
            return this.precision;
        }

        public void setPrecision(Integer precision) {
            this.precision = precision;
        }

        public String getTrimType() {
            return this.trimType;
        }

        public void setTrimType(String trimType) {
            this.trimType = trimType;
        }

        public Boolean isRepeat() {
            return this.repeat;
        }

        public void setRepeat(Boolean repeat) {
            this.repeat = repeat;
        }
    }
}
