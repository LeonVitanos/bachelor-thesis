# Human Activity Recognition System using Machine Learning techniques for Home Automation

Thesis for my BSc degree in Computer Science at Aristotle University

## Jupyter Notebook / License
Code is publicly available [here](https://github.com/LeonVitanos/Thesis/blob/master/Thesis.ipynb) or [here](https://nbviewer.jupyter.org/github/LeonVitanos/Thesis/blob/master/Thesis.ipynb). See the [LICENSE](https://github.com/LeonVitanos/Thesis/blob/master/LICENSE) file for license rights and limitations (MIT).

## Abstract

In the last two decades, Human activity recognition (HAR), as a branch of human-computer interaction and pervasive computing, has been extensively investigated and has made remarkable progress. Today, most mobile devices have fast processors with low power consumption and built-in accurate compact sensors. Data from sensors such as the accelerometer allow the creation of a HAR system using machine learning techniques. Deep learning methods have been extensively studied, as their use usually leads to better results. In contrast to statistical methods, deep learning makes it possible to automatically extract high-level features.

The scope of the thesis is to design and implement a human activity recognition system with use in assisted living technologies. The aim of the system is to identify gestures through the acceleration data of a tri-axial accelerometer. To solve this optimization problem, a heuristic approach is proposed, to achieve an optimal solution with low computational cost. The proposed approach, is the use of the Haar wavelet transform for data smoothing, and the training of a deep neural network. Feature extraction was made from the network without the use of a separate technique.

A dataset was used where eight users performed twenty repetitions of twenty different gestures, which led to the collection of 3251 sequences. From an initial set of seven classifiers, the one with the best accuracy was selected. The proposed system is able to classify gestures, executed at different speeds, with minimal pre-processing, thereby reducing the computational cost.

Finally, an Android application was developed, which incorporated the model with the highest accuracy. Based on the respective gesture that is recognized by the human activity recognition system, it is possible to automate the use of home appliances, smart home operations and more. Developments in the recognition of human activity can bring about significant changes in the lives of vulnerable social groups, such as the elderly and the disabled, as they make daily housework easier to manage.


## Περίληψη

Τις τελευταίες δύο δεκαετίες, η αναγνώριση ανθρώπινης δραστηριότητας (Human Activity Recognition - HAR), ως κλάδος της αλληλεπίδρασης ανθρώπου-υπολογιστή και της διάχυτης πληροφορικής, έχει διερευνηθεί εκτενώς με αποτέλεσμα να έχει επιτευχθεί αξιοσημείωτη πρόοδος. Σήμερα, οι περισσότερες κινητές συσκευές διαθέτουν γρήγορους επεξεργαστές χαμηλής κατανάλωσης ενέργειας και ενσωματωμένους αισθητήρες μικρού μεγέθους και μεγάλης ακριβείας. Tα δεδομένα από αισθητήρες όπως το επιταχυνσιόμετρο επιτρέπουν τη δημιουργία ενός συστήματος HAR μέσω της χρήσης τεχνικών μηχανικής μάθησης. Οι μέθοδοι βαθιάς μάθησης έχουν μελετηθεί ευρέως, καθώς η χρήση τους συνήθως οδηγεί σε καλύτερα αποτελέσματα. Σε αντίθεση με τις στατιστικές μεθόδους, η βαθιά μάθηση καθιστά δυνατή την αυτόματη εξαγωγή χαρακτηριστικών υψηλού επιπέδου.

Σκοπός της παρούσας εργασίας είναι ο σχεδιασμός και η υλοποίηση ενός συστήματος αναγνώρισης ανθρώπινης δραστηριότητας, με εφαρμογή σε τεχνολογίες υποβοηθούμενης διαβίωσης. Στόχος του συστήματος είναι η αναγνώριση χειρονομιών (Gesture Recognition) μέσω των δεδομένων επιτάχυνσης ενός τρι-αξονικού επιταχυνσιόμετρου. Για την βελτιστοποίηση της ακρίβειας του συστήματος, προτείνεται μια ευρετική προσέγγιση, ώστε να επιτευχθεί η καλύτερη λύση με το χαμηλότερο δυνατό υπολογιστικό κόστος. Η μέθοδος επίλυσης που επιλέχτηκε είναι η εξομάλυνση των δεδομένων μέσω του κυματοειδή μετασχηματισμού Haar (Haar Wavelet Transform) και η εκπαίδευση ενός βαθιού νευρωνικού δικτύου. Η εξαγωγή χαρακτηριστικών έγινε από το δίκτυο, δίχως τη χρήση κάποιας ξεχωριστής τεχνικής. 

Χρησιμοποιήθηκε μια συλλογή δεδομένων, όπου οκτώ χρήστες εκτέλεσαν είκοσι επαναλήψεις από είκοσι διαφορετικές χειρονομίες, που οδήγησαν στη συλλογή 3251 αλληλουχιών. Από το αρχικό σύνολο των επτά κατηγοριοποιητών, επιλέχθηκε εκείνος με την καλύτερη ακρίβεια. Το προτεινόμενο σύστημα είναι σε θέση να κατηγοριοποιεί χειρονομίες, που εκτελούνται σε διαφορετικές ταχύτητες, με ελάχιστη προεπεξεργασία, μειώνοντας κατά αυτόν το τρόπο το υπολογιστικό κόστος. 

Τέλος, αναπτύχθηκε μια εφαρμογή Android, στην οποία ενσωματώθηκε το μοντέλο κατηγοριοποίησης με τη μεγαλύτερη ακρίβεια. Με βάση την εκάστοτε εκτελούμενη χειρονομία, η οποία αναγνωρίζεται από το σύστημα αναγνώρισης ανθρώπινης δραστηριότητας, είναι εφικτή η αυτοματοποίηση της χρήσης οικιακών συσκευών, των ενεργειών ενός έξυπνου σπιτιού, και άλλα. Οι εξελίξεις στον τομέα της αναγνώρισης ανθρώπινης δραστηριότητας, μπορούν να επιφέρουν σημαντικές αλλαγές στις ζωές ευπαθών κοινωνικών ομάδων, όπως ηλικιωμένα άτομα και άτομα με ειδικές ανάγκες, καθώς καθιστούν τις καθημερινές οικιακές λειτουργίες ευκολότερα διαχειρίσιμες.
