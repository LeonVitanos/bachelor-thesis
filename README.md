# Human Activity Recognition System using Machine Learning techniques for Home Automation

Thesis for my BSc degree in Computer Science at Aristotle University

## Jupyter Notebook / License
Code is publicly available [here](https://github.com/LeonVitanos/Thesis/blob/master/Thesis.ipynb) or [here](https://nbviewer.jupyter.org/github/LeonVitanos/Thesis/blob/master/Thesis.ipynb). See the [LICENSE](https://github.com/LeonVitanos/Thesis/blob/master/LICENSE) file for license rights and limitations (MIT).

## Abstract

Human activity recognition (HAR), as a branch of human-computer interaction and pervasive computing, has been extensively investigated over the last two decades and significant progress has been made. Today, most mobile devices have fast processors with low power consumption and built-in accurate compact sensors. Data from sensors such as the accelerometer allow the creation of a HAR system using machine learning techniques. Deep learning methods have been extensively studied, as their use leads to better results. In contrast to statistical methods, deep learning makes it possible to automatically extract high-level features.

The scope of this thesis is to design and implement a human activity recognition system with use in assisted living technologies. The aim of the system is to identify gestures through the acceleration data of a tri-axial accelerometer. To solve this optimization problem, a heuristic approach is proposed, to achieve an optimal solution with low computational cost. The proposed approach, is the use of the Haar wavelet transform for data smoothing, and the training of a deep neural network. Feature extraction was made from the network, without the use of a separate technique.

We used a dataset where eight users performed twenty repetitions of twenty different gestures to collect 3251 sequences. From an initial set of seven classifiers, the one with the best accuracy was selected. The proposed system is able to classify gestures, executed at different speeds, with minimal pre-processing, reducing the computational cost.

Finally, an Android application was developed which incorporated the model with the highest accuracy. The application communicates to a Raspberry Pi after recognizing the gesture performed by the user. Based on the respective gesture, it is possible to automate home appliances, smart home operations and more.


## Περίληψη

Η αναγνώριση ανθρώπινης δραστηριότητας (Human Activity Recognition - HAR), ως κλάδος της αλληλεπίδρασης ανθρώπου-υπολογιστή και της διάχυτης πληροφορικής, έχει διερευνηθεί εκτενώς τις τελευταίες δύο δεκαετίες και έχει επιτευχθεί αξιοσημείωτη πρόοδος. Σήμερα, οι περισσότερες κινητές συσκευές έχουν γρήγορους επεξεργαστές με χαμηλή κατανάλωση ενέργειας και ενσωματωμένους αισθητήρες μικρού μεγέθους με μεγάλη ακρίβεια. Tα δεδομένα από αισθητήρες όπως το επιταχυνσιόμετρο επιτρέπουν την δημιουργία ενός συστήματος HAR με χρήση τεχνικών μηχανικής μάθησης. Οι μέθοδοι βαθιάς εκμάθησης έχουν μελετηθεί εκτενώς, αφού η χρήση τους οδηγεί σε καλύτερα αποτελέσματα. Σε αντίθεση με τις στατιστικές μεθόδους, η βαθιά μάθηση καθιστά δυνατή την αυτόματη εξαγωγή χαρακτηριστικών υψηλού επιπέδου.

Σκοπός της παρούσας εργασίας είναι ο σχεδιασμός και η υλοποίηση ενός συστήματος αναγνώρισης ανθρώπινης δραστηριότητας, με εφαρμογή σε τεχνολογίες υποβοηθούμενης διαβίωσης. Στόχος του συστήματος είναι η αναγνώριση χειρονομιών μέσω των δεδομένων επιτάχυνσης ενός τρι-αξονικού επιταχυνσιόμετρου. Για την επίλυση του προβλήματος βελτιστοποίησης της ακρίβειας του συστήματος προτείνεται μια ευρετική προσέγγιση, ώστε να επιτευχθεί βέλτιστη λύση με χαμηλό υπολογιστικό κόστος. Η μέθοδος επίλυσης που επιλέχτηκε είναι η εξομάλυνση των δεδομένων μέσω του μετασχηματισμού Haar wavelet και η εκπαίδευση ενός βαθιού νευρωνικού δικτύου. Η εξαγωγή χαρακτηριστικών έγινε από το δίκτυο, χωρίς τη χρήση κάποιας ξεχωριστής τεχνικής. 

Xρησιμοποιήθηκε μια συλλογή δεδομένων, όπου οκτώ χρήστες εκτέλεσαν είκοσι επαναλήψεις από είκοσι διαφορετικές χειρονομίες για την συλλογή 3251 αλληλουχιών. Από ένα αρχικό σύνολο επτά κατηγοριοποιητών επιλέχθηκε αυτός με την καλύτερη ακρίβεια. Το προτεινόμενο σύστημα είναι σε θέση να κατηγοριοποιεί χειρονομίες, που εκτελούνται με διαφορετικές ταχύτητες, με ελάχιστη προεπεξεργασία, μειώνοντας το υπολογιστικό κόστος.

Τέλος, αναπτύχθηκε μια εφαρμογή Android, στην οποία ενσωματώθηκε το μοντέλο με την μεγαλύτερη ακρίβεια. Η εφαρμογή αφού αναγνωρίσει την χειρονομία που εκτέλεσε ο χρήστης, επικοινωνεί με ένα Raspberry Pi. Με βάση την εκάστοτε χειρονομία είναι εφικτή η αυτοματοποίηση οικιακών συσκευών, ενεργειών ενός έξυπνου σπιτιού κ.α.
