/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Customer;
import Model.CustomerDao;
import Model.Combo;
import Model.Config;
import Model.Details;
import Model.Events;
import Model.LoginDAO;
import Model.Products;
import Model.ProductsDao;
import Model.Supplier;
import Model.SupplierDao;
import Model.Render;
import Model.Sale;
import Model.SaleDao;
import Model.LoginModel;
import Reports.Graphic;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
//AUDIO
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author USER
 */
public final class System extends javax.swing.JFrame {
    Date dateSale = new Date();
    String dateActual = new SimpleDateFormat("dd/MM/yyyy").format(dateSale);
    Customer cl = new Customer();
    CustomerDao client = new CustomerDao();
    Supplier pr = new Supplier();
    SupplierDao PrDao = new SupplierDao();
    Products pro = new Products();
    ProductsDao proDao = new ProductsDao();
    Sale v = new Sale();
    SaleDao Vdao = new SaleDao();
    Details Dv = new Details();
    Config conf = new Config();
    Events event = new Events();
    LoginModel lg = new LoginModel();
    LoginDAO LoginD = new LoginDAO();
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    int item;
    double Totalpagar = 0.00;
    
    
    public System() {
        initComponents();
    }
    public System (LoginModel priv){
        initComponents();
        this.setLocationRelativeTo(null);
        Midate.setDate(dateSale);
        txtIdCustomer.setVisible(false);
        txtDniCustomer.setVisible(false);
        txtIdSale.setVisible(false);
        txtIdPro.setVisible(false);
        txtIdproducto.setVisible(false);
        txtIdSupplier.setVisible(false);
        txtIdConfig.setVisible(false);
        txtIdCV.setVisible(false);
        cbxSupplierPro.setVisible(false);
        btnSupplier.setVisible(false);
        btnSales.setVisible(false);
        btnGraph.setVisible(false);
        jButton3.setOpaque (false);
        jButton3.setContentAreaFilled (false);
        jButton3.setBorderPainted (false);
        jButton3.setOpaque (false);
        jButton3.setContentAreaFilled(false);
        jButton3.setBorderPainted (false);
        ListConfig();
        if (priv.getRol().equals("Asistente")) {
            btnProducts.setEnabled(false);
            btnSupplier.setEnabled(false);
            LabelSeller.setText(priv.getName());
        }else{
            LabelSeller.setText(priv.getName());
        }
    }
    public void ListCustomer() {
        List<Customer> ListCl = client.ListCustomer();
        model = (DefaultTableModel) TableCustomer.getModel();
        Object[] ob = new Object[8];
        for (int i = 0; i < ListCl.size(); i++) {
            ob[0] = ListCl.get(i).getId();
            //ob[1] = ListCl.get(i).getDni();
            ob[1] = ListCl.get(i).getName();
            ob[2] = ListCl.get(i).getPhone();
            ob[3] = ListCl.get(i).getDirection();
            ob[4] = ListCl.get(i).getEmail();
            ob[5] = ListCl.get(i).getHobbies();
            ob[6] = ListCl.get(i).getDiscomfort();
            ob[7] = ListCl.get(i).getBirthdays();
            model.addRow(ob);
        }
        TableCustomer.setModel(model);
    }

    public void ListSupplier() {
        List<Supplier> ListPr = PrDao.ListSupplier();
        model = (DefaultTableModel) TableSupplier.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < ListPr.size(); i++) {
            ob[0] = ListPr.get(i).getId();
            ob[1] = ListPr.get(i).getRuc();
            ob[2] = ListPr.get(i).getName();
            ob[3] = ListPr.get(i).getPhone();
            ob[4] = ListPr.get(i).getDirection();
            model.addRow(ob);
        }
        TableSupplier.setModel(model);

    }
    
    public void ListUsers() {
        List<LoginModel> List = LoginD.ListUsers();
        model = (DefaultTableModel) TableUsers.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < List.size(); i++) {
            ob[0] = List.get(i).getId();
            ob[1] = List.get(i).getName();
            ob[2] = List.get(i).getEmail();
            ob[3] = List.get(i).getRol();
            model.addRow(ob);
        }
        TableUsers.setModel(model);

    }
    
    public void ListProducts() {
        List<Products> ListPro = proDao.ListProducts();
        
        TableProducto.setDefaultRenderer(Object.class, new Render());
        DefaultTableModel dt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        model = (DefaultTableModel) TableProducto.getModel();
        Object[] ob = new Object[11];
        for (int i = 0; i < ListPro.size(); i++) {
            ob[0] = ListPro.get(i).getId();
            ob[1] = ListPro.get(i).getCode();
            ob[2] = ListPro.get(i).getName();
            //ob[3] = ListPro.get(i).getSupplierPro();
            ob[3] = ListPro.get(i).getStock();
            ob[4] = ListPro.get(i).getPrice();
            ob[5] = ListPro.get(i).getDuration();
            try{
                byte[] bi = ListPro.get(i).getPhoto();
                BufferedImage image = null;
                InputStream in = new ByteArrayInputStream(bi);
                image = ImageIO.read(in);
                ImageIcon imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
                ob[6] = new JLabel(imgi);

            }catch(Exception ex){
                ob[6] = new JLabel("No imagen");
            }
            ob[7] = ListPro.get(i).getBenefits();
            ob[8] = ListPro.get(i).getEnpromo();
            ob[9] = ListPro.get(i).getPricepromo();
            ob[10] = ListPro.get(i).getStartend();
            model.addRow(ob);
        }
        TableProducto.setModel(model);

    }

    public void ListConfig() {
        conf = proDao.LookUpData();
        txtIdConfig.setText("" + conf.getId());
        txtRucConfig.setText("" + conf.getRuc());
        txtNameConfig.setText("" + conf.getName());
        txtPhoneConfig.setText("" + conf.getPhone());
        txtDirectionConfig.setText("" + conf.getDirection());
        txtMessage.setText("" + conf.getMessage());
    }

    public void ListSales() {
        List<Sale> ListSale = Vdao.Listsales();
        model = (DefaultTableModel) TableSales.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < ListSale.size(); i++) {
            ob[0] = ListSale.get(i).getId();
            ob[1] = ListSale.get(i).getName_cli();
            ob[2] = ListSale.get(i).getSeller();
            ob[3] = ListSale.get(i).getTotal();
            model.addRow(ob);
        }
        TableSales.setModel(model);

    }

    public void LimpiarTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnNuevaSale = new javax.swing.JButton();
        btnCustomers = new javax.swing.JButton();
        btnSupplier = new javax.swing.JButton();
        btnProducts = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        LabelSeller = new javax.swing.JLabel();
        tipo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnSales = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCodeSale = new javax.swing.JTextField();
        txtDescripcionSale = new javax.swing.JTextField();
        txtAmountSale = new javax.swing.JTextField();
        txtPriceSale = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableSale = new javax.swing.JTable();
        btnEliminatesale = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtRucSale = new javax.swing.JTextField();
        txtNameCustomersale = new javax.swing.JTextField();
        btnGenerarSale = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JLabel();
        txtIdCV = new javax.swing.JTextField();
        txtIdPro = new javax.swing.JTextField();
        btnGraph = new javax.swing.JButton();
        Midate = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCustomer = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        txtDniCustomer = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNameCustomer = new javax.swing.JTextField();
        txtPhoneCustomer = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtDirecionCustomer = new javax.swing.JTextField();
        txtIdCustomer = new javax.swing.JTextField();
        btnGuardarCustomer = new javax.swing.JButton();
        btnEditarCustomer = new javax.swing.JButton();
        btnEliminateCustomer = new javax.swing.JButton();
        btnNuevoCustomer = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        txtEmailCustomer = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txtHobbiesCustomer = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        txtDiscomfortCustomer = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        txtBirthdaysCustomer = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableSupplier = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtRucSupplier = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtNamesupplier = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtPhoneSupplier = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtDirectionSupplier = new javax.swing.JTextField();
        btnguardarSupplier = new javax.swing.JButton();
        btnEditarSupplier = new javax.swing.JButton();
        btnNuevoSupplier = new javax.swing.JButton();
        btnEliminateSupplier = new javax.swing.JButton();
        txtIdSupplier = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableProducto = new javax.swing.JTable();
        txtIdproducto = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtCodePro = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtDesPro = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtCantPro = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtPricePro = new javax.swing.JTextField();
        btnGuardarpro = new javax.swing.JButton();
        btnEditarpro = new javax.swing.JButton();
        btnEliminatePro = new javax.swing.JButton();
        btnNuevoPro = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        txtDurationPro = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        txtruta = new javax.swing.JTextField();
        txtBenefitsPro = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtEnpromotionPro = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtPricepromoPro = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtStartendPro = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        cbxSupplierPro = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableSales = new javax.swing.JTable();
        btnPdfSales = new javax.swing.JButton();
        txtIdSale = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtRucConfig = new javax.swing.JTextField();
        txtNameConfig = new javax.swing.JTextField();
        txtPhoneConfig = new javax.swing.JTextField();
        txtDirectionConfig = new javax.swing.JTextField();
        txtMessage = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtIdConfig = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        btnIniciar = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        cbxRol = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableUsers = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setBackground(new java.awt.Color(60, 63, 65));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 90, 90));

        jPanel1.setBackground(new java.awt.Color(69, 8, 206));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/aromaterapia_logo.png"))); // NOI18N

        btnNuevaSale.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevaSale.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevaSale.setForeground(new java.awt.Color(0, 0, 0));
        btnNuevaSale.setText("NEW SALE");
        btnNuevaSale.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevaSale.setFocusable(false);
        btnNuevaSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaSaleActionPerformed(evt);
            }
        });

        btnCustomers.setBackground(new java.awt.Color(255, 255, 255));
        btnCustomers.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCustomers.setForeground(new java.awt.Color(0, 0, 0));
        btnCustomers.setText("CUSTOMERS");
        btnCustomers.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCustomers.setFocusable(false);
        btnCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomersActionPerformed(evt);
            }
        });

        btnSupplier.setBackground(new java.awt.Color(255, 255, 255));
        btnSupplier.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSupplier.setForeground(new java.awt.Color(0, 0, 0));
        btnSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSupplier.setFocusable(false);
        btnSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupplierActionPerformed(evt);
            }
        });

        btnProducts.setBackground(new java.awt.Color(255, 255, 255));
        btnProducts.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnProducts.setForeground(new java.awt.Color(0, 0, 0));
        btnProducts.setText("PRODUCTS");
        btnProducts.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProducts.setFocusable(false);
        btnProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductsMouseClicked(evt);
            }
        });
        btnProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductsActionPerformed(evt);
            }
        });

        btnConfig.setBackground(new java.awt.Color(255, 255, 255));
        btnConfig.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnConfig.setForeground(new java.awt.Color(0, 0, 0));
        btnConfig.setText("CONFIGURACIÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã…â€œN");
        btnConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnConfig.setFocusable(false);
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        LabelSeller.setForeground(new java.awt.Color(255, 255, 255));
        LabelSeller.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelSeller.setText("user_name");

        tipo.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("USERS");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnSales.setBackground(new java.awt.Color(255, 255, 255));
        btnSales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSales.setForeground(new java.awt.Color(0, 0, 0));
        btnSales.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSales.setFocusable(false);
        btnSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNuevaSale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCustomers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(LabelSeller, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(tipo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnSupplier, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnSales, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(LabelSeller)
                .addGap(18, 18, 18)
                .addComponent(tipo)
                .addGap(8, 8, 8)
                .addComponent(btnNuevaSale, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSales, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(btnSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 560));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/encabezado_chad_1.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, -60, 1000, 200));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(204, 153, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("CÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â³digo");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("DescripciÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â³n");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Cant");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Price");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("STOCK DISPONIBLE");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        txtCodeSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodeSaleActionPerformed(evt);
            }
        });
        txtCodeSale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodeSaleKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodeSaleKeyTyped(evt);
            }
        });
        jPanel2.add(txtCodeSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 102, 30));

        txtDescripcionSale.setEditable(false);
        txtDescripcionSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionSaleActionPerformed(evt);
            }
        });
        txtDescripcionSale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionSaleKeyTyped(evt);
            }
        });
        jPanel2.add(txtDescripcionSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 180, 30));

        txtAmountSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAmountSaleActionPerformed(evt);
            }
        });
        txtAmountSale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAmountSaleKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAmountSaleKeyTyped(evt);
            }
        });
        jPanel2.add(txtAmountSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 40, 30));

        txtPriceSale.setEditable(false);
        txtPriceSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceSaleActionPerformed(evt);
            }
        });
        jPanel2.add(txtPriceSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 80, 30));

        txtStockDisponible.setEditable(false);
        txtStockDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockDisponibleActionPerformed(evt);
            }
        });
        jPanel2.add(txtStockDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 79, 30));

        TableSale.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DESCRIPTION", "AMOUNT", "PRICE U.", "PRICE TOTAL"
            }
        ));
        jScrollPane1.setViewportView(TableSale);
        if (TableSale.getColumnModel().getColumnCount() > 0) {
            TableSale.getColumnModel().getColumn(0).setPreferredWidth(60);
            TableSale.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableSale.getColumnModel().getColumn(2).setPreferredWidth(40);
            TableSale.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableSale.getColumnModel().getColumn(4).setPreferredWidth(60);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 1040, 191));

        btnEliminatesale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        btnEliminatesale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminatesaleActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminatesale, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 100, -1, 40));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("ID:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 352, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Name:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 352, -1, -1));

        txtRucSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRucSaleActionPerformed(evt);
            }
        });
        txtRucSale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucSaleKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucSaleKeyTyped(evt);
            }
        });
        jPanel2.add(txtRucSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 375, 116, 30));

        txtNameCustomersale.setEditable(false);
        jPanel2.add(txtNameCustomersale, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 375, 169, 30));

        btnGenerarSale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/imprimir.png"))); // NOI18N
        btnGenerarSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarSaleActionPerformed(evt);
            }
        });
        jPanel2.add(btnGenerarSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 350, 40, 45));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/dinero.png"))); // NOI18N
        jLabel10.setText("TOTAL A PAGAR:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 380, -1, -1));

        LabelTotal.setText("-----");
        jPanel2.add(LabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 380, -1, -1));
        jPanel2.add(txtIdCV, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 380, -1, -1));
        jPanel2.add(txtIdPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 110, -1, -1));

        btnGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraphActionPerformed(evt);
            }
        });
        jPanel2.add(btnGraph, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, 30, 30));
        jPanel2.add(Midate, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 60, 210, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Seleccionar:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, -1, -1));

        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("Realizar sale");
        jPanel2.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 400, -1, -1));

        jTabbedPane1.addTab("1", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "PHONE", "DIRECCIÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã…â€œN", "CORREO", "HOBBIES", "MOLESTIAS", "CUMPLEAÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã‹Å“OS"
            }
        ));
        TableCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableCustomerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableCustomer);
        if (TableCustomer.getColumnModel().getColumnCount() > 0) {
            TableCustomer.getColumnModel().getColumn(0).setPreferredWidth(3);
            TableCustomer.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableCustomer.getColumnModel().getColumn(2).setPreferredWidth(50);
            TableCustomer.getColumnModel().getColumn(3).setPreferredWidth(80);
            TableCustomer.getColumnModel().getColumn(4).setPreferredWidth(50);
            TableCustomer.getColumnModel().getColumn(5).setPreferredWidth(100);
            TableCustomer.getColumnModel().getColumn(6).setPreferredWidth(10);
            TableCustomer.getColumnModel().getColumn(7).setPreferredWidth(50);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 780, 410));

        jPanel9.setBackground(new java.awt.Color(204, 204, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtDniCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniCustomerKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Name:");

        txtPhoneCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneCustomerActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("TÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â©lefono:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("DirecciÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â³n:");

        txtDirecionCustomer.setText(" ");
        txtDirecionCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirecionCustomerActionPerformed(evt);
            }
        });

        txtIdCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdCustomerActionPerformed(evt);
            }
        });

        btnGuardarCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/save.png"))); // NOI18N
        btnGuardarCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCustomerActionPerformed(evt);
            }
        });

        btnEditarCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        btnEditarCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditarCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCustomerActionPerformed(evt);
            }
        });

        btnEliminateCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        btnEliminateCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminateCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminateCustomerActionPerformed(evt);
            }
        });

        btnNuevoCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/new.png"))); // NOI18N
        btnNuevoCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevoCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCustomerActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("Guardar");
        jLabel40.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel40.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Actualizar");
        jLabel41.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel41.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Eliminate");
        jLabel42.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel42.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("Nuevo");
        jLabel43.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel43.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 0, 0));
        jLabel53.setText("CUSTOMERS");

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 0));
        jLabel54.setText("CumpleaÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â±os:");

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("Email:");

        txtHobbiesCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHobbiesCustomerActionPerformed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 0));
        jLabel56.setText("Hobbies:");

        txtDiscomfortCustomer.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDiscomfortCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtDiscomfortCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscomfortCustomerActionPerformed(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 0, 0));
        jLabel57.setText("Discomfort:");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel55)
                                    .addComponent(jLabel54))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmailCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBirthdaysCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel56)
                                    .addComponent(jLabel57))
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(txtHobbiesCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtDiscomfortCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel53))
                    .addComponent(txtIdCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtDniCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel41))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(btnGuardarCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditarCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btnEliminateCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoCustomer)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDirecionCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(txtPhoneCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(txtNameCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))))
                .addGap(67, 67, Short.MAX_VALUE))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtEmailCustomer, txtBirthdaysCustomer, txtDirecionCustomer, txtHobbiesCustomer, txtNameCustomer, txtPhoneCustomer});

        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhoneCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(9, 9, 9)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDirecionCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBirthdaysCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHobbiesCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel57)
                        .addGap(33, 33, 33)
                        .addComponent(txtIdCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 99, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(txtDiscomfortCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnNuevoCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnEliminateCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnEditarCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnGuardarCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtDniCustomer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel42))))
                        .addContainerGap())))
        );

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 280, 410));

        jTabbedPane1.addTab("2", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NAME", "ID", "RUC", "PHONE", "DIRECCIÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã…â€œN"
            }
        ));
        TableSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableSupplierMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableSupplier);
        if (TableSupplier.getColumnModel().getColumnCount() > 0) {
            TableSupplier.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableSupplier.getColumnModel().getColumn(1).setPreferredWidth(40);
            TableSupplier.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableSupplier.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableSupplier.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 57, 720, 310));

        jPanel10.setBackground(new java.awt.Color(255, 204, 204));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Supplier"));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Ruc:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Name:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("TelÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â©fono:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("DirecciÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â³n:");

        btnguardarSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/save.png"))); // NOI18N
        btnguardarSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarSupplierActionPerformed(evt);
            }
        });

        btnEditarSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        btnEditarSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarSupplierActionPerformed(evt);
            }
        });

        btnNuevoSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/new.png"))); // NOI18N
        btnNuevoSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoSupplierActionPerformed(evt);
            }
        });

        btnEliminateSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        btnEliminateSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminateSupplierActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("Guardar");

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("Actualizar");

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setText("Eliminate");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("Nuevo");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 51, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel17))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtNamesupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                        .addComponent(txtRucSupplier)))
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel19)
                                        .addComponent(jLabel20))
                                    .addGap(24, 24, 24)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtPhoneSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                        .addComponent(txtDirectionSupplier))))
                            .addComponent(btnguardarSupplier))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(txtIdSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel45)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnEditarSupplier))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(btnEliminateSupplier)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnNuevoSupplier)))
                        .addGap(62, 62, 62))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel47)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtRucSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtNamesupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtPhoneSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel20))
                    .addComponent(txtDirectionSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtIdSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnguardarSupplier)
                    .addComponent(btnEditarSupplier))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminateSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoSupplier))
                .addGap(1, 1, 1)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47))
                .addGap(6, 6, 6))
        );

        jPanel4.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 260, 350));

        jTabbedPane1.addTab("3", jPanel4);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableProducto.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        TableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "NAME", "STOCK", "PRECIO", "DURACION", "FOTO", "BENEFICIOS", "ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¿PROMOCION?", "PRECIO PROMO", "INICIO/FIN"
            }
        ));
        TableProducto.setRowHeight(60);
        TableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableProducto);
        if (TableProducto.getColumnModel().getColumnCount() > 0) {
            TableProducto.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableProducto.getColumnModel().getColumn(3).setPreferredWidth(40);
            TableProducto.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 800, 400));
        jPanel5.add(txtIdproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

        jPanel11.setBackground(new java.awt.Color(255, 204, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("CÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â³digo:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Name:");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Amount:");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Price:");

        txtPricePro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPriceProKeyTyped(evt);
            }
        });

        btnGuardarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/save.png"))); // NOI18N
        btnGuardarpro.setMaximumSize(new java.awt.Dimension(30, 30));
        btnGuardarpro.setMinimumSize(new java.awt.Dimension(30, 30));
        btnGuardarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarproActionPerformed(evt);
            }
        });

        btnEditarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        btnEditarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarproActionPerformed(evt);
            }
        });

        btnEliminatePro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/delete.png"))); // NOI18N
        btnEliminatePro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminateProActionPerformed(evt);
            }
        });

        btnNuevoPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/new.png"))); // NOI18N
        btnNuevoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProActionPerformed(evt);
            }
        });

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Guardar");

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Actualizar");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Eliminate");

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("Nuevo");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 0, 0));
        jLabel58.setText("PRODUCTS");

        txtDurationPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDurationProActionPerformed(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 0, 0));
        jLabel59.setText("DuraciÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â³n:");

        jButton2.setText("Subir Imagen");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 0, 0));
        jLabel60.setText("Imagen:");

        txtruta.setEditable(false);
        txtruta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrutaActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Benefits:");

        txtEnpromotionPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnpromotionProActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¿promotion?:");

        txtPricepromoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPricepromoProActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Price Promo:");

        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 0, 0));
        jLabel61.setText("Inicio / Fin:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel58))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(btnGuardarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEditarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(btnEliminatePro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51)
                            .addComponent(btnNuevoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addComponent(jLabel22)
                                                .addGap(45, 45, 45))
                                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel23)
                                            .addComponent(jLabel60))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPricePro)
                                    .addComponent(txtCantPro)
                                    .addComponent(txtDesPro)
                                    .addComponent(txtCodePro)
                                    .addComponent(txtDurationPro)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel61))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPricepromoPro)
                                    .addComponent(txtruta, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(txtBenefitsPro)
                                    .addComponent(txtEnpromotionPro)
                                    .addComponent(txtStartendPro))))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodePro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDesPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPricePro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDurationPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel60))
                .addGap(0, 0, 0)
                .addComponent(txtruta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBenefitsPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEnpromotionPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPricepromoPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStartendPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminatePro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardarpro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel50)
                        .addComponent(jLabel49)
                        .addComponent(jLabel48))
                    .addComponent(jLabel51, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jPanel5.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 260, 400));

        cbxSupplierPro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSupplierProItemStateChanged(evt);
            }
        });
        cbxSupplierPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSupplierProActionPerformed(evt);
            }
        });
        jPanel5.add(cbxSupplierPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 21, -1));

        jTabbedPane1.addTab("4", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 102));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CUSTOMER", "VENDEDOR", "TOTAL"
            }
        ));
        TableSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableSalesMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableSales);
        if (TableSales.getColumnModel().getColumnCount() > 0) {
            TableSales.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableSales.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableSales.getColumnModel().getColumn(2).setPreferredWidth(60);
            TableSales.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        jPanel6.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 920, 310));

        btnPdfSales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf.png"))); // NOI18N
        btnPdfSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfSalesActionPerformed(evt);
            }
        });
        jPanel6.add(btnPdfSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));
        jPanel6.add(txtIdSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 46, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("HISTORIAL OF SALES");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 280, -1));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("Export PDF");
        jPanel6.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        jTabbedPane1.addTab("5", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("NO. SUCURSAL");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, -1, -1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setText("NAME");
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, -1, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("PHONE");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, -1, -1));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("DIRECCIÃƒÆ’Ã†â€™ÃƒÂ¢Ã¢â€šÂ¬Ã…â€œN");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, -1, -1));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("MESSAGE");
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, -1, -1));
        jPanel7.add(txtRucConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 147, 30));
        jPanel7.add(txtNameConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 220, 30));
        jPanel7.add(txtPhoneConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 218, 30));
        jPanel7.add(txtDirectionConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 147, 30));
        jPanel7.add(txtMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 400, 30));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("DATA OF THE COMPANY");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        jPanel8.setBackground(new java.awt.Color(153, 255, 204));

        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar.png"))); // NOI18N
        btnActualizarConfig.setText("ACTUALIZAR");
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(136, Short.MAX_VALUE)
                .addComponent(btnActualizarConfig)
                .addGap(88, 88, 88)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(250, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnActualizarConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 420, 310));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/empresa.png"))); // NOI18N
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, 410, 290));

        jTabbedPane1.addTab("6", jPanel7);

        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(255, 204, 255));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/candado.png"))); // NOI18N

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Email ElectrÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â³nico:");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("ContraseÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â±a:");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        btnIniciar.setBackground(new java.awt.Color(153, 0, 255));
        btnIniciar.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciar.setText("Register");
        btnIniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Name:");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Rol:");

        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Asistente" }));
        cbxRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxRolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel36)
                            .addComponent(jLabel35)
                            .addComponent(jLabel34)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .addComponent(txtPass)
                            .addComponent(jLabel37)
                            .addComponent(txtName)
                            .addComponent(cbxRol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addGap(114, 114, 114))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addGap(2, 2, 2)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxRol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel12.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 280, 380));

        TableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Email", "Rol"
            }
        ));
        jScrollPane6.setViewportView(TableUsers);

        jPanel12.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 670, 380));

        jTabbedPane1.addTab("7", jPanel12);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 95, 1080, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomersActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListCustomer();
        btnEditarCustomer.setEnabled(false);
        btnEliminateCustomer.setEnabled(false);
        LimpiarCustomer();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnCustomersActionPerformed

    private void btnSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupplierActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListSupplier();
        jTabbedPane1.setSelectedIndex(2);
        btnEditarSupplier.setEnabled(true);
        btnEliminateSupplier.setEnabled(true);
        LimpiarSupplier();
    }//GEN-LAST:event_btnSupplierActionPerformed

    private void btnProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductsActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListProducts();
        jTabbedPane1.setSelectedIndex(3);
        btnEditarpro.setEnabled(false);
        btnEliminatePro.setEnabled(false);
        btnGuardarpro.setEnabled(true);
        LimpiarProducts();
    }//GEN-LAST:event_btnProductsActionPerformed

    private void btnNuevaSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaSaleActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaSaleActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
        LimpiarTable();
        ListSales();
    }//GEN-LAST:event_btnSalesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(6);
        LimpiarTable();
        ListUsers();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductsMouseClicked
        // TODO add your handling code here:
        cbxSupplierPro.removeAllItems();
        llenarSupplier();
        
    }//GEN-LAST:event_btnProductsMouseClicked

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        if(txtName.getText().equals("") || txtEmail.getText().equals("") || txtPass.getPassword().equals("")){
            JOptionPane.showMessageDialog(null, "Todo los campos son requeridos");
        }else{
            String email = txtEmail.getText();
            String pass = String.valueOf(txtPass.getPassword());
            String nom = txtName.getText();
            String rol = cbxRol.getSelectedItem().toString();
            lg.setName(nom);
            lg.setEmail(email);
            lg.setPass(pass);
            lg.setRol(rol);
            LoginD.Register(lg);
            JOptionPane.showMessageDialog(null, "User Registrado");
            LimpiarTable();
            ListUsers();
            nuevoUser();
        }
    }//GEN-LAST:event_btnIniciarActionPerformed
    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucConfig.getText()) || !"".equals(txtNameConfig.getText()) || !"".equals(txtPhoneConfig.getText()) || !"".equals(txtDirectionConfig.getText())) {
            conf.setRuc(txtRucConfig.getText());
            conf.setName(txtNameConfig.getText());
            conf.setPhone(txtPhoneConfig.getText());
            conf.setDirection(txtDirectionConfig.getText());
            conf.setMessage(txtMessage.getText());
            conf.setId(Integer.parseInt(txtIdConfig.getText()));
            proDao.ModifyData(conf);
            JOptionPane.showMessageDialog(null, "Data de la empresa modificado");
            ListConfig();
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void btnPdfSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfSalesActionPerformed

        if(txtIdSale.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }else{
            v = Vdao.LookUpSale(Integer.parseInt(txtIdSale.getText()));
            Vdao.pdfV(v.getId(), v.getCustomer(), v.getTotal(), v.getSeller());
        }
    }//GEN-LAST:event_btnPdfSalesActionPerformed

    private void TableSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableSalesMouseClicked
        // TODO add your handling code here:
        int fila = TableSales.rowAtPoint(evt.getPoint());
        txtIdSale.setText(TableSales.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_TableSalesMouseClicked

    private void TableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductoMouseClicked
        // TODO add your handling code here:
        btnEditarpro.setEnabled(true);
        btnEliminatePro.setEnabled(true);
        btnGuardarpro.setEnabled(false);
        int fila = TableProducto.rowAtPoint(evt.getPoint());
        txtIdproducto.setText(TableProducto.getValueAt(fila, 0).toString());
        txtCodePro.setText(TableProducto.getValueAt(fila, 1).toString());
        txtDesPro.setText(TableProducto.getValueAt(fila, 2).toString());
        txtCantPro.setText(TableProducto.getValueAt(fila, 3).toString());
        txtPricePro.setText(TableProducto.getValueAt(fila, 4).toString());
        txtDurationPro.setText(TableProducto.getValueAt(fila, 5).toString());
        txtBenefitsPro.setText(TableProducto.getValueAt(fila, 6).toString());
        txtEnpromotionPro.setText(TableProducto.getValueAt(fila, 7).toString());
        txtPricepromoPro.setText(TableProducto.getValueAt(fila, 8).toString());
        txtStartendPro.setText(TableProducto.getValueAt(fila, 9).toString());
        txtruta.setText("");
    }//GEN-LAST:event_TableProductoMouseClicked

    private void btnEliminateSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminateSupplierActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdSupplier.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminate");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdSupplier.getText());
                PrDao.EliminateSupplier(id);
                LimpiarTable();
                ListSupplier();
                LimpiarSupplier();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminateSupplierActionPerformed

    private void btnNuevoSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoSupplierActionPerformed
        // TODO add your handling code here:
        LimpiarSupplier();
        btnEditarSupplier.setEnabled(false);
        btnEliminateSupplier.setEnabled(false);
        btnguardarSupplier.setEnabled(true);
    }//GEN-LAST:event_btnNuevoSupplierActionPerformed

    private void btnEditarSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarSupplierActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdSupplier.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtRucSupplier.getText()) || !"".equals(txtNamesupplier.getText()) || !"".equals(txtPhoneSupplier.getText()) || !"".equals(txtDirectionSupplier.getText())) {
                pr.setRuc(txtRucSupplier.getText());
                pr.setName(txtNamesupplier.getText());
                pr.setPhone(txtPhoneSupplier.getText());
                pr.setDirection(txtDirectionSupplier.getText());
                pr.setId(Integer.parseInt(txtIdSupplier.getText()));
                PrDao.ModifySupplier(pr);
                JOptionPane.showMessageDialog(null, "Supplier Modificado");
                LimpiarTable();
                ListSupplier();
                LimpiarSupplier();
                btnEditarSupplier.setEnabled(false);
                btnEliminateSupplier.setEnabled(false);
                btnguardarSupplier.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarSupplierActionPerformed

    private void btnguardarSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarSupplierActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucSupplier.getText()) || !"".equals(txtNamesupplier.getText()) || !"".equals(txtPhoneSupplier.getText()) || !"".equals(txtDirectionSupplier.getText())) {
            pr.setRuc(txtRucSupplier.getText());
            pr.setName(txtNamesupplier.getText());
            pr.setPhone(txtPhoneSupplier.getText());
            pr.setDirection(txtDirectionSupplier.getText());
            PrDao.RegisterSupplier(pr);
            JOptionPane.showMessageDialog(null, "Supplier Registrado");
            LimpiarTable();
            ListSupplier();
            LimpiarSupplier();
            btnEditarSupplier.setEnabled(false);
            btnEliminateSupplier.setEnabled(false);
            btnguardarSupplier.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos esta vacios");
        }
    }//GEN-LAST:event_btnguardarSupplierActionPerformed

    private void TableSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableSupplierMouseClicked
        // TODO add your handling code here:
        btnEditarSupplier.setEnabled(true);
        btnEliminateSupplier.setEnabled(true);
        btnguardarSupplier.setEnabled(false);
        int fila = TableSupplier.rowAtPoint(evt.getPoint());
        txtIdSupplier.setText(TableSupplier.getValueAt(fila, 0).toString());
        txtRucSupplier.setText(TableSupplier.getValueAt(fila, 1).toString());
        txtNamesupplier.setText(TableSupplier.getValueAt(fila, 2).toString());
        txtPhoneSupplier.setText(TableSupplier.getValueAt(fila, 3).toString());
        txtDirectionSupplier.setText(TableSupplier.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableSupplierMouseClicked

    private void btnNuevoCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCustomerActionPerformed
        // TODO add your handling code here:
        LimpiarCustomer();
        btnEditarCustomer.setEnabled(false);
        btnEliminateCustomer.setEnabled(false);
        btnGuardarCustomer.setEnabled(true);
    }//GEN-LAST:event_btnNuevoCustomerActionPerformed

    private void btnEliminateCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminateCustomerActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdCustomer.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminate");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCustomer.getText());
                client.EliminateCustomer(id);
                LimpiarTable();
                LimpiarCustomer();
                ListCustomer();
                JOptionPane.showMessageDialog(null, "Customer eliminado");
            }
        }
    }//GEN-LAST:event_btnEliminateCustomerActionPerformed

    private void btnEditarCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCustomerActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdCustomer.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {

            if (!"".equals(txtNameCustomer.getText()) || !"".equals(txtPhoneCustomer.getText()) || !"".equals(txtDirecionCustomer.getText()) || !"".equals(txtEmailCustomer.getText()) || !"".equals(txtHobbiesCustomer.getText()) || !"".equals(txtDiscomfortCustomer.getText())) {
                //cl.setDni(txtDniCustomer.getText());
                cl.setName(txtNameCustomer.getText());
                cl.setPhone(txtPhoneCustomer.getText());
                cl.setDirection(txtDirecionCustomer.getText());
                cl.setEmail(txtEmailCustomer.getText());
                cl.setHobbies(txtHobbiesCustomer.getText());
                cl.setDiscomfort(txtDiscomfortCustomer.getText());
                cl.setBirthdays(txtBirthdaysCustomer.getText());
                cl.setId(Integer.parseInt(txtIdCustomer.getText()));
                client.ModifyCustomer(cl);
                JOptionPane.showMessageDialog(null, "Customer Modificado");
                LimpiarTable();
                LimpiarCustomer();
                ListCustomer();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }//GEN-LAST:event_btnEditarCustomerActionPerformed

    private void btnGuardarCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCustomerActionPerformed
        // TODO add your handling code here:
        //!"".equals(txtDniCustomer.getText()) ||
        if (!"".equals(txtNameCustomer.getText()) || !"".equals(txtPhoneCustomer.getText()) || !"".equals(txtDirecionCustomer.getText()) || !"".equals(txtEmailCustomer.getText()) || !"".equals(txtHobbiesCustomer.getText()) || !"".equals(txtDiscomfortCustomer.getText()) || !"".equals(txtBirthdaysCustomer.getText())) {
            //cl.setDni(txtDniCustomer.getText());
            cl.setName(txtNameCustomer.getText());
            cl.setPhone(txtPhoneCustomer.getText());
            cl.setDirection(txtDirecionCustomer.getText());
            cl.setEmail(txtEmailCustomer.getText());
            cl.setHobbies(txtHobbiesCustomer.getText());
            cl.setDiscomfort(txtDiscomfortCustomer.getText());
            cl.setBirthdays(txtBirthdaysCustomer.getText());
            client.RegisterCustomer(cl);
            JOptionPane.showMessageDialog(null, "Customer Registrado");
            LimpiarTable();
            LimpiarCustomer();
            ListCustomer();
            btnEditarCustomer.setEnabled(false);
            btnEliminateCustomer.setEnabled(false);
            btnGuardarCustomer.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Hay campos vacios");
        }
    }//GEN-LAST:event_btnGuardarCustomerActionPerformed

    private void TableCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableCustomerMouseClicked
        // TODO add your handling code here:
        btnEditarCustomer.setEnabled(true);
        btnEliminateCustomer.setEnabled(true);
        btnGuardarCustomer.setEnabled(false);
        int fila = TableCustomer.rowAtPoint(evt.getPoint());
        txtIdCustomer.setText(TableCustomer.getValueAt(fila, 0).toString());
        //txtDniCustomer.setText(TableCustomer.getValueAt(fila, 1).toString());
        txtNameCustomer.setText(TableCustomer.getValueAt(fila, 1).toString());
        txtPhoneCustomer.setText(TableCustomer.getValueAt(fila, 2).toString());
        txtDirecionCustomer.setText(TableCustomer.getValueAt(fila, 3).toString());
        txtEmailCustomer.setText(TableCustomer.getValueAt(fila, 4).toString());
        txtHobbiesCustomer.setText(TableCustomer.getValueAt(fila, 5).toString());
        txtDiscomfortCustomer.setText(TableCustomer.getValueAt(fila, 6).toString());
        txtBirthdaysCustomer.setText(TableCustomer.getValueAt(fila, 7).toString());
    }//GEN-LAST:event_TableCustomerMouseClicked

    private void btnGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraphActionPerformed
        // TODO add your handling code here:

        String dateReporte = new SimpleDateFormat("dd/MM/yyyy").format(Midate.getDate());
        Graphic.Graph(dateReporte);

    }//GEN-LAST:event_btnGraphActionPerformed

    private void btnGenerarSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarSaleActionPerformed
        // TODO add your handling code here:
        if (TableSale.getRowCount() > 0) {
            if (!"".equals(txtNameCustomersale.getText())) {
                RegisterSale();
                RegisterDetails();
                ActualizarStock();
                LimpiarTableSale();
                LimpiarCustomersale();
                JOptionPane.showMessageDialog(null, "Sale realizada");
            } else {
                JOptionPane.showMessageDialog(null, "Debes lookUp un customer");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Noy products en la sale");
        }
    }//GEN-LAST:event_btnGenerarSaleActionPerformed

    private void txtRucSaleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucSaleKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRucSaleKeyTyped

    private void txtRucSaleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucSaleKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtRucSale.getText())) {
                int id = Integer.parseInt(txtRucSale.getText());
                cl = client.LookUpcustomer(id);
                if (cl.getName() != null) {
                    txtNameCustomersale.setText("" + cl.getName());
                    txtIdCV.setText("" + cl.getId());
                } else {
                    txtRucSale.setText("");
                    JOptionPane.showMessageDialog(null, "El customer no existe");
                }
            }
        }
    }//GEN-LAST:event_txtRucSaleKeyPressed

    private void btnEliminatesaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminatesaleActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel) TableSale.getModel();
        model.removeRow(TableSale.getSelectedRow());
        TotalPagar();
        txtCodeSale.requestFocus();
    }//GEN-LAST:event_btnEliminatesaleActionPerformed

    private void txtAmountSaleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountSaleKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtAmountSaleKeyTyped

    private void txtAmountSaleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountSaleKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtAmountSale.getText())) {
                int id = Integer.parseInt(txtIdPro.getText());
                String descripcion = txtDescripcionSale.getText();
                int cant = Integer.parseInt(txtAmountSale.getText());
                double price = Double.parseDouble(txtPriceSale.getText());
                double total = cant * price;
                int generalcont = 0;
                int stock = Integer.parseInt(txtStockDisponible.getText());
                if (stock >= cant) {
                    item = item + 1;
                    tmp = (DefaultTableModel) TableSale.getModel();
                    for (int i = 0; i < TableSale.getRowCount(); i++) {
                        if (TableSale.getValueAt(i, 1).equals(txtDescripcionSale.getText())) {
                            int aux = Integer.parseInt(TableSale.getValueAt(i, 2).toString());
                            generalcont = generalcont + aux;
                        }
                    }
                    if(generalcont >= stock){
                        JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                        return;
                    }
                    ArrayList list = new ArrayList();
                    list.add(item);
                    list.add(id);
                    list.add(descripcion);
                    list.add(cant);
                    list.add(price);
                    list.add(total);
                    Object[] O = new Object[5];
                    O[0] = list.get(1);
                    O[1] = list.get(2);
                    O[2] = list.get(3);
                    //O[3] = list.get(4);
                    O[3] = String.format("%,.0f",list.get(4));
                    O[4] = list.get(5);
                    tmp.addRow(O);
                    TableSale.setModel(tmp);
                    TotalPagar();
                    LimparSale();
                    txtCodeSale.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Amount");
            }
        }
    }//GEN-LAST:event_txtAmountSaleKeyPressed

    private void txtDescripcionSaleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionSaleKeyTyped
        // TODO add your handling code here:
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionSaleKeyTyped

    private void txtCodeSaleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeSaleKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodeSaleKeyTyped

    private void txtCodeSaleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeSaleKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodeSale.getText())) {
                String cod = txtCodeSale.getText();
                pro = proDao.LookUpPro(cod);
                if (pro.getName() != null) {
                    txtIdPro.setText("" + pro.getId());
                    txtDescripcionSale.setText("" + pro.getName());
                    txtPriceSale.setText("" + pro.getPrice());
                    txtStockDisponible.setText("" + pro.getStock());
                    txtAmountSale.requestFocus();
                } else {
                    LimparSale();
                    txtCodeSale.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el code de producto");
                txtCodeSale.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodeSaleKeyPressed

    private void cbxRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxRolActionPerformed

    private void txtPriceSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceSaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceSaleActionPerformed

    private void txtDescripcionSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionSaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionSaleActionPerformed

    private void txtDirecionCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirecionCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDirecionCustomerActionPerformed

    private void txtHobbiesCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHobbiesCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHobbiesCustomerActionPerformed

    private void txtIdCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdCustomerActionPerformed

    private void txtDiscomfortCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscomfortCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiscomfortCustomerActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
        j.setFileFilter(fil);
        
        int s = j.showOpenDialog(this);
        if(s == JFileChooser.APPROVE_OPTION){
            String ruta = j.getSelectedFile().getAbsolutePath();
            txtruta.setText(ruta);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtDurationProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDurationProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDurationProActionPerformed

    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        // TODO add your handling code here:
        LimpiarProducts();
    }//GEN-LAST:event_btnNuevoProActionPerformed

    private void btnEliminateProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminateProActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdproducto.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminate");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdproducto.getText());
                proDao.EliminateProducts(id);
                LimpiarTable();
                LimpiarProducts();
                ListProducts();
                btnEditarpro.setEnabled(false);
                btnEliminatePro.setEnabled(false);
                btnGuardarpro.setEnabled(true);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnEliminateProActionPerformed

    private void btnEditarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarproActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdproducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtCodePro.getText()) || !"".equals(txtDesPro.getText()) || !"".equals(txtCantPro.getText()) || !"".equals(txtPricePro.getText())) {
                pro.setCode(txtCodePro.getText());
                pro.setName(txtDesPro.getText());
                //Combo itemP = (Combo) cbxSupplierPro.getSelectedItem();
                //pro.setSupplier(itemP.getId());
                pro.setStock(Integer.parseInt(txtCantPro.getText()));
                pro.setPrice(Double.parseDouble(txtPricePro.getText()));
                pro.setDuration(Integer.parseInt(txtDurationPro.getText()));
                pro.setBenefits(txtBenefitsPro.getText());
                pro.setEnpromo(txtEnpromotionPro.getText());
                pro.setPricepromo(Double.parseDouble(txtPricepromoPro.getText()));
                pro.setStartend(txtStartendPro.getText());
                // Photo
                File ruta = new File(txtruta.getText());
                
                if (ruta.exists() && ruta.canRead()) {
                    try {
                        byte[] icono = new byte[(int) ruta.length()];
                        InputStream input = new FileInputStream(ruta);
                        input.read(icono);
                        pro.setPhoto(icono);
                        pro.setId(Integer.parseInt(txtIdproducto.getText()));
                        proDao.ModifyProductsImagen(pro);
                    } catch(Exception ex) {
                        JOptionPane.showMessageDialog(null, "Hubo un error al leer la imagen del archivo. La imagen no se modifyÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡.");
                        pro.setId(Integer.parseInt(txtIdproducto.getText()));
                        proDao.ModifyProducts(pro);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo de la imagen no existe o no se puede leer. La imagen no se modifyÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¡.");
                    pro.setId(Integer.parseInt(txtIdproducto.getText()));
                    proDao.ModifyProducts(pro);
                }

                JOptionPane.showMessageDialog(null, "Producto Modificado");
                LimpiarTable();
                ListProducts();
                LimpiarProducts();
                cbxSupplierPro.removeAllItems();
                llenarSupplier();
                btnEditarpro.setEnabled(false);
                btnEliminatePro.setEnabled(false);
                btnGuardarpro.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarproActionPerformed

    private void btnGuardarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarproActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtCodePro.getText()) || !"".equals(txtDesPro.getText()) || !"".equals(txtCantPro.getText()) || !"".equals(txtPricePro.getText()) || !"".equals(txtDurationPro.getText())|| !"".equals(txtBenefitsPro.getText())|| !"".equals(txtEnpromotionPro.getText())|| !"".equals(txtPricepromoPro.getText())|| !"".equals(txtStartendPro.getText()) ) {
            pro.setCode(txtCodePro.getText());
            pro.setName(txtDesPro.getText());
            //Combo itemP = (Combo) cbxSupplierPro.getSelectedItem();
            //pro.setSupplier(itemP.getId());
            pro.setStock(Integer.parseInt(txtCantPro.getText()));
            pro.setPrice(Double.parseDouble(txtPricePro.getText()));
            pro.setDuration(Integer.parseInt(txtDurationPro.getText()));
            pro.setBenefits(txtBenefitsPro.getText());
            pro.setEnpromo(txtEnpromotionPro.getText());
            pro.setPricepromo(Double.parseDouble(txtPricepromoPro.getText()));
            pro.setStartend(txtStartendPro.getText());
            // Photo
            File ruta = new File(txtruta.getText());
            //pro.setPhoto(Bytes.parseInt(txtRuta));
            try{
                byte[] icono = new byte[(int) ruta.length()];
                InputStream input = new FileInputStream(ruta);
                input.read(icono);
                pro.setPhoto(icono);
            }catch(Exception ex){
                pro.setPhoto(null);
            }
            
            proDao.RegisterProducts(pro);
            JOptionPane.showMessageDialog(null, "Products Registrado");
            LimpiarTable();
            LimpiarProducts();
            ListProducts();
            cbxSupplierPro.removeAllItems();
            llenarSupplier();
            btnEditarpro.setEnabled(false);
            btnEliminatePro.setEnabled(false);
            btnGuardarpro.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarproActionPerformed

    private void txtPriceProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceProKeyTyped
        // TODO add your handling code here:
        event.numberDecimalKeyPress(evt, txtPricePro);
    }//GEN-LAST:event_txtPriceProKeyTyped

    private void txtDniCustomerKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniCustomerKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniCustomerKeyTyped

    private void txtrutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrutaActionPerformed

    private void cbxSupplierProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSupplierProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxSupplierProActionPerformed

    private void cbxSupplierProItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSupplierProItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxSupplierProItemStateChanged

    private void txtAmountSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountSaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmountSaleActionPerformed

    private void txtCodeSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodeSaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodeSaleActionPerformed

    private void txtRucSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRucSaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRucSaleActionPerformed

    private void txtStockDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockDisponibleActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            // Localiza el archivo de sonido
            File soundFile = new File("A:\\Principal\\Documentos\\NetBeansProjects\\System-sale-java-v1-main\\src\\Img\\etesech.wav"); // Actualiza con tu ruta de archivo
            // Crea un AudioInputStream a partir del archivo de sonido
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Obtiene un clip de sonido
            Clip clip = AudioSystem.getClip();
            // Abre el clip de audio
            clip.open(audioIn);
            // Comienza a reproducir el sonido
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtPhoneCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneCustomerActionPerformed

    private void txtEnpromotionProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnpromotionProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnpromotionProActionPerformed

    private void txtPricepromoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPricepromoProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPricepromoProActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(System.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(System.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(System.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(System.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new System().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelTotal;
    private javax.swing.JLabel LabelSeller;
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JTable TableCustomer;
    private javax.swing.JTable TableProducto;
    private javax.swing.JTable TableSupplier;
    private javax.swing.JTable TableUsers;
    private javax.swing.JTable TableSale;
    private javax.swing.JTable TableSales;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnCustomers;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnEditarCustomer;
    private javax.swing.JButton btnEditarSupplier;
    private javax.swing.JButton btnEditarpro;
    private javax.swing.JButton btnEliminateCustomer;
    private javax.swing.JButton btnEliminatePro;
    private javax.swing.JButton btnEliminateSupplier;
    private javax.swing.JButton btnEliminatesale;
    private javax.swing.JButton btnGenerarSale;
    private javax.swing.JButton btnGraph;
    private javax.swing.JButton btnGuardarCustomer;
    private javax.swing.JButton btnGuardarpro;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnNuevaSale;
    private javax.swing.JButton btnNuevoCustomer;
    private javax.swing.JButton btnNuevoPro;
    private javax.swing.JButton btnNuevoSupplier;
    private javax.swing.JButton btnPdfSales;
    private javax.swing.JButton btnProducts;
    private javax.swing.JButton btnSupplier;
    private javax.swing.JButton btnSales;
    private javax.swing.JButton btnguardarSupplier;
    private javax.swing.JComboBox<Object> cbxSupplierPro;
    private javax.swing.JComboBox<String> cbxRol;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel tipo;
    private javax.swing.JTextField txtBenefitsPro;
    private javax.swing.JTextField txtCantPro;
    private javax.swing.JTextField txtAmountSale;
    private javax.swing.JTextField txtCodePro;
    private javax.swing.JTextField txtCodeSale;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmailCustomer;
    private javax.swing.JTextField txtBirthdaysCustomer;
    private javax.swing.JTextField txtDesPro;
    private javax.swing.JTextField txtDescripcionSale;
    private javax.swing.JTextField txtDirectionConfig;
    private javax.swing.JTextField txtDirectionSupplier;
    private javax.swing.JTextField txtDirecionCustomer;
    private javax.swing.JTextField txtDniCustomer;
    private javax.swing.JTextField txtDurationPro;
    private javax.swing.JTextField txtEnpromotionPro;
    private javax.swing.JTextField txtHobbiesCustomer;
    private javax.swing.JTextField txtIdCV;
    private javax.swing.JTextField txtIdCustomer;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdPro;
    private javax.swing.JTextField txtIdSupplier;
    private javax.swing.JTextField txtIdSale;
    private javax.swing.JTextField txtIdproducto;
    private javax.swing.JTextField txtStartendPro;
    private javax.swing.JTextField txtMessage;
    private javax.swing.JTextField txtDiscomfortCustomer;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameCustomer;
    private javax.swing.JTextField txtNameCustomersale;
    private javax.swing.JTextField txtNameConfig;
    private javax.swing.JTextField txtNamesupplier;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtPricePro;
    private javax.swing.JTextField txtPriceSale;
    private javax.swing.JTextField txtPricepromoPro;
    private javax.swing.JTextField txtRucConfig;
    private javax.swing.JTextField txtRucSupplier;
    private javax.swing.JTextField txtRucSale;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtPhoneCustomer;
    private javax.swing.JTextField txtPhoneConfig;
    private javax.swing.JTextField txtPhoneSupplier;
    private javax.swing.JTextField txtruta;
    // End of variables declaration//GEN-END:variables
    private void LimpiarCustomer() {
        txtIdCustomer.setText("");
        txtDniCustomer.setText("");
        txtNameCustomer.setText("");
        txtPhoneCustomer.setText("");
        txtDirecionCustomer.setText("");
        txtEmailCustomer.setText("");
        txtHobbiesCustomer.setText("");
        txtDiscomfortCustomer.setText("");
        txtBirthdaysCustomer.setText("");
    }

    private void LimpiarSupplier() {
        txtIdSupplier.setText("");
        txtRucSupplier.setText("");
        txtNamesupplier.setText("");
        txtPhoneSupplier.setText("");
        txtDirectionSupplier.setText("");
    }

    private void LimpiarProducts() {
        txtIdPro.setText("");
        txtCodePro.setText("");
        //cbxSupplierPro.setSelectedItem(null);
        txtDesPro.setText("");
        txtCantPro.setText("");
        txtPricePro.setText("");
        txtDurationPro.setText("");
        txtruta.setText("");
        txtBenefitsPro.setText("");
        txtEnpromotionPro.setText("");
        txtPricepromoPro.setText("");
        txtStartendPro.setText("");
    }

    private void TotalPagar() {
        Totalpagar = 0.00;
        int numFila = TableSale.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(TableSale.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;
        }
        LabelTotal.setText(String.format("%.2f", Totalpagar));
    }

    private void LimparSale() {
        txtCodeSale.setText("");
        txtDescripcionSale.setText("");
        txtAmountSale.setText("");
        txtStockDisponible.setText("");
        txtPriceSale.setText("");
        txtIdSale.setText("");
    }

    private void RegisterSale() {
        int customer = Integer.parseInt(txtIdCV.getText());
        //String customer = txtNameCustomersale.getText();
        String seller = LabelSeller.getText();
        double monto = Totalpagar;
        v.setCustomer(customer);
        v.setSeller(seller);
        v.setTotal(monto);
        v.setDate(dateActual);
        Vdao.RegisterSale(v);
    }

    private void RegisterDetails() {
        int id = Vdao.IdSale();
        for (int i = 0; i < TableSale.getRowCount(); i++) {
            int id_pro = Integer.parseInt(TableSale.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableSale.getValueAt(i, 2).toString());
            double price = Double.parseDouble(TableSale.getValueAt(i, 3).toString());
            Dv.setId_pro(id_pro);
            Dv.setAmount(cant);
            Dv.setPrice(price);
            Dv.setId(id);
            Vdao.RegisterDetails(Dv);

        }
        int customer = Integer.parseInt(txtIdCV.getText());
        Vdao.pdfV(id, customer, Totalpagar, LabelSeller.getText());
    }

    private void ActualizarStock() {
        for (int i = 0; i < TableSale.getRowCount(); i++) {
            int id = Integer.parseInt(TableSale.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableSale.getValueAt(i, 2).toString());
            pro = proDao.LookUpId(id);
            int stockardo = pro.getStock();
            if (cant > stockardo) {
                JOptionPane.showMessageDialog(null, "La amount solicitada es mayor que el stock disponible.");
            } else {
                int StockActual = stockardo - cant;
                Vdao.ActualizarStock(StockActual, id);
            }

            }
    }

    private void LimpiarTableSale() {
        tmp = (DefaultTableModel) TableSale.getModel();
        int fila = TableSale.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

    private void LimpiarCustomersale() {
        txtRucSale.setText("");
        txtNameCustomersale.setText("");
        txtIdCV.setText("");
    }
    private void nuevoUser(){
        txtName.setText("");
        txtEmail.setText("");
        txtPass.setText("");
    }
    private void llenarSupplier(){
        List<Supplier> list = PrDao.ListSupplier();
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            cbxSupplierPro.addItem(new Combo(id, name));
        }
    }
}
