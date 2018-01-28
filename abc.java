package multiAgentSystem;

import java.util.Random;

public class abc_1 {

	static int num_particle = 10;

	static int Imax = 100;

	static int min_x = -5;

	static int max_x = 5;

	static double x[][];

	static double fitx[];

	static double v[][];

	static double fitv[];

	static double Xbest[];

	static double Fbest;

	static int t[];

	static int limit = 10;

	public static void main(String[] args) {

		initialize();

		for (int i = 0; i < Imax; i++) {

			harbestBee();

			followUpBee();

			reconnaissanceBee();

			// �o��
			if (i % 10 == 0) {
				System.out.println(
						i + "," + Xbest[0] + "," + Xbest[1] + "," + criterion(Xbest[0], Xbest[1]));
			}

		}

	}

	// ����֐�
	public static double criterion(double x1, double x2) {

		double f = x1 * x1 + x2 * x2;

		return f;

	}

	public static void initialize() {

		t = new int[num_particle];
		x = new double[num_particle][2];
		fitx = new double[num_particle];
		v = new double[num_particle][2];
		fitv = new double[num_particle];
		Xbest = new double[2];

		// �T���_�̃����_�������A�K���x�̌v�Z
		for (int i = 0; i < num_particle; i++) {

			t[i] = 0;

			x[i][0] = Math.random() * (max_x - min_x) + min_x;
			x[i][1] = Math.random() * (max_x - min_x) + min_x;

			if (criterion(x[i][0], x[i][1]) >= 0) {
				fitx[i] = 1 / (1 + criterion(x[i][0], x[i][1]));
			} else {
				fitx[i] = 1 - criterion(x[i][0], x[i][1]);
			}

		}

		// �ŗǉ��A�֐��l
		double tFbest = fitx[0];
		int tIbest = 0;
		for (int i = 1; i < num_particle; i++) {

			if (fitx[i] > tFbest) {
				tFbest = fitx[i];
				tIbest = i;
			}

		}

		Xbest[0] = x[tIbest][0];
		Xbest[1] = x[tIbest][1];
		Fbest = tFbest;

	}

	public static void harbestBee() {

		for (int i = 0; i < num_particle; i++) {

			// �����̑I��
			int dimension;
			if (Math.random() < 0.5) {
				dimension = 0;
			} else {
				dimension = 1;
			}

			Random rnd = new Random();

			int k = rnd.nextInt(num_particle);

			double phi = Math.random() * 2 - 1;

			v[i][dimension] = x[i][dimension] + phi * (x[i][dimension] - x[k][dimension]);
			v[i][1 - dimension] = x[i][1 - dimension];

			// v�̓K���x
			if (criterion(v[i][0], v[i][1]) >= 0) {
				fitv[i] = 1 / (1 + criterion(v[i][0], v[i][1]));
			} else {
				fitv[i] = 1 - criterion(v[i][0], v[i][1]);
			}

			// �X�V
			if (fitv[i] > fitx[i]) {

				x[i][0] = v[i][0];
				x[i][1] = v[i][1];

				fitx[i] = fitv[i];

				t[i] = 0;

			} else {

				t[i] = t[i] + 1;

			}

		}

	}

	public static void followUpBee() {

		// Pi�̌v�Z
		double[] p = new double[num_particle];

		for (int i = 0; i < num_particle; i++) {

			p[i] = 0.9 * fitx[i] / Fbest + 0.1;

		}

		// �T���_�̑I��
		// ���Ίm���̌v�Z
		for (int i = 0; i < num_particle; i++) {

			p[i] = 0.9 * fitx[i] / Fbest + 0.1;

		}

		// �m���̍��v�̌v�Z
		double max = 0;
		for (int i = 0; i < num_particle; i++) {
			max += p[i];
		}

		// x[][]�̃R�s�[
		double[][] copy_x = new double[num_particle][2];

		for (int i = 0; i < num_particle; i++) {

			for (int j = 0; j < 2; j++) {

				copy_x[i][j] = x[i][j];

			}

		}

		// �T���_�̑I��
		int index = 0;
		double temp = Math.random() * max;
		for (int i = 0; i < num_particle; i++) {
			temp -= p[i];
			if (temp < 0) {
				x[index] = copy_x[i];
				index++;
				break;

			}
		}

		// �T��
		harbestBee();

		// best�̍X�V
		for (int i = 0; i < num_particle; i++) {

			if (fitx[i] > Fbest) {
				Fbest = fitx[i];
				Xbest[0] = x[i][0];
				Xbest[1] = x[i][1];
			}

		}

	}

	public static void reconnaissanceBee() {
		for (int i = 0; i < num_particle; i++) {

			if (t[i] > limit) {

				t[i] = 0;

				x[i][0] = Math.random() * (max_x - min_x) + min_x;
				x[i][1] = Math.random() * (max_x - min_x) + min_x;

				if (criterion(x[i][0], x[i][1]) >= 0) {
					fitx[i] = 1 / (1 + criterion(x[i][0], x[i][1]));
				} else {
					fitx[i] = 1 - criterion(x[i][0], x[i][1]);
				}

			}

		}

	}

}
