package codersafterdark.reskillable.base.configs.json;

import codersafterdark.reskillable.api.data.LockKey;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.base.LevelLockHandler;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CustomLockJson implements JsonDeserializer<LockJson>/*, JsonSerializer<BaseLockTypeJson>*/ {
    @Override
    public LockJson deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String type = obj.get("type").getAsString();
        Class<? extends LockKey> clazz = LevelLockHandler.getLockKeyClass(type);


        // Construct the holder
        JsonArray array = obj.getAsJsonArray("requirements");
        String[] stringArray = new String[array.size()];
        for (int i = 0; i < array.size(); i++) {
            stringArray[i] = array.get(i).getAsString();
        }

        RequirementHolder holder = RequirementHolder.fromStringList(stringArray);

        // Construct the LockKey
        obj.remove("type");
        obj.remove("requirements");

        LockKey key = context.deserialize(obj, clazz);

        return new LockJson(holder, key);
    }

    /*
    Not needed for now
    @Override
    public JsonElement serialize(BaseLockTypeJson src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
    */
}