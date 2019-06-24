package net.fayola.hydraulica;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "supplier")
public class Supplier {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @NonNull
    private String mName;

    public Supplier(int id, @NonNull String name) {
        this.id = id;
        this.mName = name;
    }

    @Ignore
    public Supplier(@NonNull String name) {
        this.mName = name;
    }

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    public String getName(){
        return this.mName;
    }

    @NonNull
    @Override
    public String toString() {
        return this.mName;
    }
}
