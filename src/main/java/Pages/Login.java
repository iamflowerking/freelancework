package Pages;

import WebDriverManager.WebDriverManager;

public class Login extends WebDriverManager{

	public void _login_Enter_UserName(String sUsername) {
		try {
			PerformActionOnElement("Login_Textbox_UserName", "EnterText", sUsername);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public void _login_Enter_Password(String sPassword) {
		try {
			PerformActionOnElement("Login_Textbox_Password", "EnterText", sPassword);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public void _login_Click_LoginButton() {
		try {
			PerformActionOnElement("Login_Button_Login", "Click", "");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	public String _login_ErrorMessage() {
		String sResult = "";
		try {
			sResult = FindAnElement("Login_Error").getText();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return sResult;
	}
}
