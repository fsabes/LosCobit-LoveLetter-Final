package metricas;

public class CardDimension {

	private int posXInicial;
	private int posYInicial;

	private int angle;

	private int image_xscale;
	private int image_yscale;

	public int offsetX;
	public int offsetY;

	private int escalaXOriginal;
	private int escalaYOriginal;

	public int width;
	public int height;

	static public int anchoOriginal = 170;
	static public int altoOriginal = 320;

	public CardDimension(int posX, int posY, int width, int height) {
		image_xscale = 1;
		image_yscale = 1;
		this.offsetX = posX;
		this.offsetY = posY;

		this.width = width;
		this.height = height;

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPosXInicial() {
		return posXInicial;
	}

	public void setPosXInicial(int posXInicial) {
		this.posXInicial = posXInicial;
	}

	public int getPosYInicial() {
		return posYInicial;
	}

	public void setPosYInicial(int posYInicial) {
		this.posYInicial = posYInicial;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getImage_xscale() {
		return image_xscale;
	}

	public void setImage_xscale(int image_xscale) {
		this.image_xscale = image_xscale;
	}

	public int getImage_yscale() {
		return image_yscale;
	}

	public void setImage_yscale(int image_yscale) {
		this.image_yscale = image_yscale;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public int getEscalaXOriginal() {
		return escalaXOriginal;
	}

	public void setEscalaXOriginal(int escalaXOriginal) {
		this.escalaXOriginal = escalaXOriginal;
	}

	public int getEscalaYOriginal() {
		return escalaYOriginal;
	}

	public void setEscalaYOriginal(int escalaYOriginal) {
		this.escalaYOriginal = escalaYOriginal;
	}

}
