import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np 

sns.set_theme(style="whitegrid")
df = pd.read_csv('penguins.csv')
print(df.head())




print("Shape:", df.shape)          # number of rows & columns
print("\nInfo:")
print(df.info())                   # data types + missing values
print("\nMissing values:")
print(df.isnull().sum())           # count missing values
print("\nSummary statistics:")
print(df.describe())               # numeric summary




# Drop rows missing important numeric values
df_clean = df.dropna(subset=["bill_length_mm", "flipper_length_mm"])

# Fill missing sex values
df_clean["sex"] = df_clean["sex"].fillna("Unknown")

# Check again
print(df_clean.isnull().sum())




fig, ax = plt.subplots(2, 2, figsize=(12, 10))

# A) Histogram
sns.histplot(data=df_clean, x="bill_length_mm", hue="species", kde=True, ax=ax[0,0])
ax[0,0].set_title("Bill Length by Species")

# B) Boxplot
sns.boxplot(data=df_clean, x="species", y="flipper_length_mm", ax=ax[0,1])
ax[0,1].set_title("Flipper Length by Species")

# C) Scatterplot
sns.scatterplot(data=df_clean, x="bill_length_mm", y="body_mass_g", hue="species", ax=ax[1,0])
ax[1,0].set_title("Bill Length vs Body Mass")

# D) Countplot
sns.countplot(data=df_clean, x="island", hue="species", ax=ax[1,1])
ax[1,1].set_title("Species by Island")
plt.xticks(rotation=45)

plt.tight_layout()
plt.show()




corr = df_clean[["bill_length_mm", "bill_depth_mm", "flipper_length_mm", "body_mass_g"]].corr()

sns.heatmap(corr, annot=True, cmap="coolwarm")
plt.title("Feature Correlations")
plt.show()



