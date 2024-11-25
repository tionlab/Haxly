
package com.tionlab;

import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.File;
import javax.swing.*;

public class FileDropHandler extends DropTargetAdapter {
    private final Main main;

    public FileDropHandler(JComponent component, Main main) {
        new DropTarget(component, DnDConstants.ACTION_COPY, this, true, null);
        this.main = main;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void drop(DropTargetDropEvent event) {
        try {
            event.acceptDrop(DnDConstants.ACTION_COPY);
            Transferable transferable = event.getTransferable();

            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                java.util.List<File> files = (java.util.List<File>) transferable
                        .getTransferData(DataFlavor.javaFileListFlavor);
                if (!files.isEmpty()) {
                    main.selectedFile = files.get(0);
                    FileUtils.displayFileInfo(main);
                    main.algorithmComboBox.setEnabled(true);
                    main.hashButton.setEnabled(true);
                    main.showMainUI();
                }
            }
            event.dropComplete(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            event.dropComplete(false);
        }
    }
}