package net.fayola.hydraulica;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "engine_part",
        foreignKeys = {
                @ForeignKey(entity = CarModel.class,
                        parentColumns = "_id",
                        childColumns = "cm_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = Supplier.class,
                        parentColumns = "_id",
                        childColumns = "s_id",
                        onDelete = CASCADE)
        },
        indices= {
            @Index(value = "cm_id"), @Index(value = "s_id")
        })
public class EnginePart {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @NonNull
    @ColumnInfo(name = "cm_id")
    private int mCarModelId;

    @NonNull
    @ColumnInfo(name = "s_id")
    private int mSupplierId;

    @NonNull
    private String mName;

    @NonNull
    private int mAmount;

    public EnginePart(int id, int carModelId, int supplierId, @NonNull String name, int amount){

        this.id = id;
        this.mCarModelId = carModelId;
        this.mSupplierId = supplierId;
        this.mName = name;
        if (amount < 0) { //can't have negative in stock
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        this.mAmount = amount;

    }

    @Ignore
    public EnginePart(int carModelId, int supplierId, @NonNull String name, int amount){

        this.mCarModelId = carModelId;
        this.mSupplierId = supplierId;

        this.mName = name;
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        this.mAmount = amount;

    }

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    public int getMCarModelId() {
        return mCarModelId;
    }

    @NonNull
    public int getMSupplierId() {
        return mSupplierId;
    }

    @NonNull
    public int getMAmount() {
        return mAmount;
    }

    @NonNull
    public String getMName() {
        return mName;
    }

    @NonNull
    @Override
    public String toString() {
        return mName + ": " + mAmount + "left.";
    }
}
