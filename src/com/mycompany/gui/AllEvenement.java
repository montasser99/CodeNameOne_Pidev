/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.gui;


import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.gui.BaseForm;
import com.mycompany.myapp.entities.Evenement;
import com.mycompany.services.ServiceEvenement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class AllEvenement extends BaseForm {
        Form current;
           ImageViewer imgv;

    public AllEvenement(Resources res) {
        
        super("Evenements", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Evenements");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        addTab(swipe, res.getImage("eventsssss.jpg"), spacer1, "  ", "", " ");
                
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        Component.setSameSize(radioContainer, spacer1);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
                                            
        Button Ajouter = new Button("Ajouter");
        Ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                    new AddEvenement(res, current).show();
            }
        });
                            ServiceEvenement sa =new ServiceEvenement();

                Button pdf = new Button("PDF");
        pdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 
                sa.pdf();
                
            }
        });
                            add(pdf);
        add(Ajouter);
        
        ButtonGroup barGroup = new ButtonGroup();
                  Container co=new Container(BoxLayout.xCenter());;
                    ArrayList <Evenement> evenement = new ArrayList();
                    evenement=sa.getAllEvenements();

                 for (Evenement fi : evenement) {
                            Container ct = new Container(BoxLayout.y());
                            String url = "file://C://Users//pc//Desktop//Taktak_Symfony-ChaimaGhribi//public//uploads//"+ fi.getImage();
                            int deviceWidth = Display.getInstance().getDisplayWidth();
                            Image placeholder = Image.createImage( deviceWidth/3,  deviceWidth/3, 0xbfc9d2);
                            EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
                            Image i = URLImage.createToStorage(encImage, "fileNameInStoragez" + fi.getImage(),url, URLImage.RESIZE_SCALE);
                                imgv = new ImageViewer();
                                imgv.setImage(i);
                                ct.add(imgv);

                            Label l = new Label("ID : "+fi.getId());
                            Label l2 = new Label("Titre: "+fi.getTitreEve(),"RedLabel");
                            Label l4 = new Label("Description : "+fi.getDescEve(),"SmallLabel");
                            Label l3 = new Label("Date debut : "+fi.getDate_debut(),"SmallLabel");
                            Label p = new Label("Date fin : "+fi.getDate_fin(),"SmallLabel");
                            Label l5 = new Label("Prix : "+fi.getPrix(),"SmallLabel");
                            l2.getAllStyles().setFgColor(0xf15f5f);
                            ct.add(l);
                            ct.add(l2);
                            ct.add(l4);
                            ct.add(l3);
                            ct.add(p);
                            ct.add(l5);
                            

                            Button Modif = new Button("Modifier");
                            Button Supprimer = new Button("Supprimer");
                            Modif.addActionListener(new ActionListener() {
                                            @Override
            public void actionPerformed(ActionEvent evt) {               
                                new ModifierEvenement(res,current,fi).show();                   
                                                    }   
                                            });
                          Supprimer.addActionListener(new ActionListener() {
                                            @Override
            public void actionPerformed(ActionEvent evt) {               
                if (Dialog.show("Confirmation", "Voulez vous supprimer cett evenement ?", "Oui", "Annuler")) {

                  if(ServiceEvenement.getInstance().deleteEvenement(fi.getId()))
                            {
                                Dialog.show("Success","supprimer",new Command("OK"));
                                new AllEvenement(res).show();
                            }

                            }
                   
                }   
        });
                       ct.add(Modif);
                       ct.add(Supprimer);


                       Label separator = new Label("","Separator");
                       ct.add(separator);
                       add(ct);
               }
    }
        
    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
    
    }
