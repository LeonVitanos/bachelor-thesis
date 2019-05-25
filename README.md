

```python
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import os
import pywt

#Load dataset
training_data = []
USERS = ["U01", "U02", "U03", "U04", "U05", "U06", "U07", "U08"]
GESTURES = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"]
for user in USERS:
    user_path = os.path.join("gestures-dataset", user)
    for gesture in GESTURES:
        class_num = GESTURES.index(gesture)
        path = os.path.join(user_path, gesture)
        for sequence in os.listdir(path):
            sequence_array = pd.read_csv(os.path.join(path, sequence), sep=" ", header=None, usecols=[3, 4, 5])

            x = np.concatenate((pywt.wavedec(sequence_array.iloc[:,0], 'db1')))
            y = np.concatenate((pywt.wavedec(sequence_array.iloc[:,1], 'db1')))
            z = np.concatenate((pywt.wavedec(sequence_array.iloc[:,2], 'db1')))
            xyz = np.concatenate((x[:8],y[:8],z[:8]))

            training_data.append([xyz,class_num])
            
import random
random.shuffle(training_data)

#Load features and labels
X = []
y = []
for features, label in training_data:
    X.append(features)
    y.append(label)
```


```python
len(X)
```




    3251




```python

```
