/*
 * MediathekView
 * Copyright (C) 2008 W. Xaver
 * W.Xaver[at]googlemail.com
 * http://zdfmediathk.sourceforge.net/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package mediathek.daten;

import javax.swing.event.EventListenerList;
import mediathek.Daten;
import mediathek.Konstanten;
import mediathek.MediathekGui;
import mediathek.controller.filme.filmeImportieren.MediathekListener;
import mediathek.controller.filme.filmeImportieren.MediathekTimer;
import mediathek.controller.io.History;
import mediathek.controller.io.IoXmlLesen;
import mediathek.controller.io.IoXmlSchreiben;
import mediathek.controller.io.LogDownload;
import mediathek.controller.io.starter.StarterClass;
import mediathek.gui.*;
import mediathek.gui.dialog.DialogDatenFilm;
import mediathek.tool.GuiFunktionen;

public final class DDaten extends Daten {
    // Listen

    public static ListeFilme listeFilmeNachBlackList = null;
    public ListeBlacklist listeBlacklist;
    public ListePgruppe listePgruppe = null;
//    public ListeProg listeProgVorlagen = null;
    public ListeAbo listeAbo = null;
    public ListeDownloads listeDownloads = null;
    public History history = null;
    public PanelListe panelListe = new PanelListe();
    public LogDownload log = null;
    // globale Objekte
    public IoXmlLesen ioXmlLesen = null;
    public IoXmlSchreiben ioXmlSchreiben = null;
    public StarterClass starterClass = null;
    public InfoPanel infoPanel = new InfoPanel();
    // Panel
    public MediathekGui mediathekGui = null;
    public GuiFilme guiFilme = null;
    public GuiDownloads guiDownloads = null;
    public GuiAbo guiAbo = null;
    public GuiDebug guiDebug = null;
    // Dialoge
    public DialogDatenFilm dialogDatenFilm = null;

    public DDaten(String basis) {
        super(basis);
        listeFilmeNachBlackList = new ListeFilme();
        listeBlacklist = new ListeBlacklist();
        listePgruppe = new ListePgruppe();
//        listeProgVorlagen = new ListeProg();
        listeAbo = new ListeAbo(this);
        listeDownloads = new ListeDownloads(this);
        log = new LogDownload(this);
        //initialisieren
        ioXmlLesen = new IoXmlLesen();
        ioXmlSchreiben = new IoXmlSchreiben();
        history = new History(getBasisVerzeichnis(true) + Konstanten.LOG_DATEI_HISTORY);
        dialogDatenFilm = new DialogDatenFilm(null, false, this);
        starterClass = new StarterClass(this);
    }

    @Override
    public void allesLaden() {
        super.allesLaden();
        ioXmlLesen.datenLesen(this);
        history.laden();
    }

    @Override
    public void allesSpeichern() {
        super.allesSpeichern();
        ioXmlSchreiben.datenSchreiben(this);
    }

    @Override
    public void allesAbbrechen() {
        if (starterClass != null) {
            starterClass.abbrechen();
        }
    }

    public void allesAbbrechenNachFilm() {
        if (starterClass != null) {
            starterClass.abbrechenNachFilm();
        }
    }
    // geändert

    @Override
    public void setGeaendertPanel() {
        panelListe.aendern();
        Daten.setGeaendert();
    }

    @Override
    public void setGeaendertPanelSofort() {
        panelListe.aendern();
        panelListe.aendernSofort();
        Daten.setGeaendert();
    }

}
