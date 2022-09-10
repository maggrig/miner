package mytest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphicApp {
    private JFrame frame;
    JLabel display;
    private GraphicPanel graphicPanel;
    public GraphicApp(){

        createFrame();
        initElements();
    }

    private void createFrame() {
        frame = new JFrame("Сапер");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                NewGame(graphicPanel.getHeight(),graphicPanel.getWidth());
            }
        });
    }
    public void show(){
        frame.setVisible(true);
    }
    private void initElements() {
        Container mainContainer = frame.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        display = new JLabel();
        JPanel bottomPanel = new JPanel(); // нижняя панель состояния
        bottomPanel.setBackground(Color.lightGray); // фон светло-серый
        mainContainer.add(bottomPanel, BorderLayout.SOUTH); // распологается внизу

        Box leftPanel = createLeftPanel(); // создаем левую панель в другом методе
        mainContainer.add(leftPanel, BorderLayout.WEST); // эта панель будет слева
        graphicPanel = new GraphicPanel();
        graphicPanel.setBackground(Color.decode("#2811F6"));
        mainContainer.add(graphicPanel);
    }
    private Box createLeftPanel() {
        Box panel = Box.createVerticalBox();  // вертикальный Box
        // Box это контейнер, в котором элементы выстраиваются в одном порядке

//        JLabel title = new JLabel("<html>Построение графика функции</html>");
  //       чтобы добавить перевод строки в тексте, нужно писать в тегах <html>
//        title.setFont(new Font(null, Font.BOLD, 12)); // изменяем шрифт
//        panel.add(title);

        panel.add(Box.createVerticalStrut(20)); //в Box можно добавлять отступы
        panel.add(Box.createVerticalGlue()); // также в Box можно добавлять заполнитель пустого места

        JButton button_clear = new JButton("Очистить поле"); // Кнопка
        panel.add(button_clear);
        button_clear.addActionListener(event->ClearGame());
        JButton button = new JButton("Новая игра"); // Кнопка
        panel.add(button);
        button.addActionListener(event->NewGame(graphicPanel.getHeight(),graphicPanel.getWidth()));

        return panel;

    }
    public void ClearGame()
    {
        graphicPanel.defeat();
    }
    public void NewGame(int height,int width){
        graphicPanel.init_game(graphicPanel.getHeight(),graphicPanel.getWidth());
//        graphicPanel.init_game(height,width);
    }
}