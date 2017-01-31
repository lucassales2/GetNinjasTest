package lucassales.com.getninjastest.network.dto;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 30/01/17.
 */

public class InfoDto {
    @SerializedName("label")
    private String mLabel;

    private List<String> mValues;

    public String getLabel() {
        return mLabel;
    }

    public List<String> getValues() {
        return mValues;
    }

    private void setValues(List<String> mValue) {
        this.mValues = mValue;
    }

    public String getValuesString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mValues.size(); i++) {
            String value = mValues.get(i);
            if (i == 0) {
                builder.append(value);
            } else {
                builder.append(", " + value);
            }
        }
        return builder.toString();
    }

    public static class InfoValuesDeserilizer implements JsonDeserializer<InfoDto> {

        @Override
        public InfoDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            InfoDto infoDto = new Gson().fromJson(json, InfoDto.class);
            JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.has("value")) {
                JsonElement elem = jsonObject.get("value");
                if (elem != null && !elem.isJsonNull()) {
                    if (elem.isJsonPrimitive()) {
                        List<String> values = new ArrayList<>();
                        String elemAsString = elem.getAsString();
                        if (!TextUtils.isEmpty(elemAsString)) {
                            values.add(elemAsString);
                        }
                        infoDto.setValues(values);
                    } else if (elem.isJsonArray()) {
                        List<String> values = new ArrayList<>();
                        for (JsonElement jsonElement : elem.getAsJsonArray()) {
                            values.add(jsonElement.getAsString());
                        }
                        infoDto.setValues(values);
                    }
                }
            }
            return infoDto;
        }
    }
}
