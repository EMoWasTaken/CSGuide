<!DOCTYPE html>
<html>
<head>
    <title>CS-Guide</title>
    <meta charset="UTF-8">
    <meta name="description" content="Modul-Ratgeber für Informatik-Studenten an der Goethe-Universität Frankfurt.">
    <meta name="author" content="Elias Morello">
    <link href="/styles.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script>
        const tagList = [<#list tagNames as tagName>"${tagName}"<#if tagName_has_next>,</#if></#list>];
        console.log(tagList);
        const profList = [<#list profNames as profName>"${profName}"<#if profName_has_next>,</#if></#list>];
        console.log(profList);
    </script>
    <script src="/script.js"></script>
</head>
<body>
<h1>CS-Guide</h1>
<h2>Modulratgeber für Informatikstudenten der Goethe-Universität</h2>
<p>Im weiteren Studienverlauf Ihres Informatikstudiums an der Goethe-Universität müssen Sie Wahlpflichtmodule aus dem Vertiefungsbereich im Wert von mindestens 39 CP belegen.</p>
<p>Am Ende der ersten zwei oder drei Semester bemerken viele Studenten, dass sie wenig Überblick über die Vertiefungsmodule besitzen und keine wirkliche Einschätzung haben, welche dieser Module sie optimalerweise auswählen möchten.</p>
<p><em>CS-Guide</em> soll Ihnen bei dieser Entscheidung behilflich sein und Ihnen einen Überblick über diejenigen Wahlpflichtmodule geben, die am ehesten zu Ihnen passen und zugleich auch den Anforderungen der Prüfungsordnung genügen.</p>
<p>Dazu beantworten Sie einige Fragen bzgl. Ihrer Interessen und Sympathien für die Dozenten und erhalten eine Liste derjenigen Vertiefungsmodule, die zu Ihren Angaben passen.</p>
<p>Bitte beachten Sie, dass dieses Tool Ihnen lediglich <em>Vorschläge</em> für mögliche Vertiefungsmodule liefert. Diese Vorschläge sind weder verbindlich, noch ersetzen Sie eine professionelle Studienberatung (eine solche erhalten Sie z.B. bei Frau Düffel im Lernzentrum). Sie sollen Ihnen bloß einige Anhaltspunkte bei der Auswahl Ihrer Module geben. Die endgültige Entscheidung darüber, welche Vertiefungsmodule Sie belegen möchten, müssen Sie selbst treffen, und sollte erst nach intensiver Auseinandersetzung mit dem Modulkatalog und der Prüfungsordnung erfolgen.</p>
<h3>Interessengebiete</h3>
<p>Verwenden Sie die Schieberegler, um Ihr Interesse an den angegebenen Themen darzustellen (Schieberegler links = wenig Interesse, Schieberegler rechts = hohes Interesse). Wenn Sie bei einem Stichpunkt nicht wissen, was er bedeutet, so lassen Sie den Regler am besten unverändert in der Mitte.</p>
<ul>
    <#list tags as tag>
        <li>
            <p>${tag}</p>
            <input type="range" id="tag-slider-${tag.name}" min="1" max="10" value="5">
        </li>
    </#list>
</ul>
<h3>Dozenten</h3>
<p>Verwenden Sie die Schieberegler, um für jeden der angegebenen Dozenten anzugeben, wie gut Sie mit ihm bzw. seiner Art zu lehren zurechtkommen (Schieberegler links = sehr unbeliebt, Schieberegler rechts = sehr beliebt). Wenn Sie einen Dozenten nicht kennen, so lassen Sie den Regler am besten unverändert in der Mitte.</p>
<ul>
    <#list profs as prof>
        <li>
            <p>${prof}</p>
            <img src="${prof.imgLink}" alt="Kein Bild verfuegbar">
            <input type="range" id="prof-slider-${prof.name}" min="1" max="10" value="5">
        </li>
    </#list>
</ul>
<h3>Auswertung</h3>
<p><em>CS-Guide</em> bietet Ihnen zwei Algorithmen an, um die passendsten Module zu ermitteln - Greedy Pick und Linear Sum. Optimalerweise sollten Sie beide ausprobieren und vergleichen.</p>
<p>Durch Ihre Angaben erhält jedes Modul ein <em>Rating</em>, eine Zahl zwischen 0 und 1, welche die Relevanz des Moduls für Sie widerspiegelt.</p>
<p><b>Greedy-Pick</b> nutzt, wie der Name bereits andeutet, ein Greedy-Verfahren, welches nacheinander die Module mit dem aktuell höchsten Rating entnimmt und darstellt (unter Beachtung der Richtlinien bzgl. Seminare und Praktika).</p>
<p><b>Linear Sum</b> nutzt lineare Optimierung, um die Summe der Ratings aller ausgewählten Module zu maximieren.</p>
<p>Am Ende des Tages müssen Sie die Entscheidung über Ihre gewählten Module selbst treffen. Diese Auflistungen sind nur Empfehlungen, von daher lohnt es sich, mehrere Möglichkeiten in Betracht zu ziehen.</p>
<input id="greedyPick" type="submit" value="Greedy Pick">
<input id="linearSum" type="submit" value="Linear Sum">
<p id="result_announce"></p>
<ul id="result_list"></ul>
</body>
</html>