package multiAgentSystem;

import java.util.Random;

public class pso_1 {

	static int num_particle;

	static int min_x;

	static int max_x;

	static double x[][];

	static double v[][];

	static double Pbest[][];

	static double Gbest[];

	public static void main(String[] args) {

		num_particle = 10;

		min_x = -5;

		max_x = 5;

		// x,v�̏�����
		initialize();

		// �J��Ԃ���
		int T = 100;

		for (int t = 0; t < T; t++) {

			for (int i = 0; i < num_particle; i++) {
				// �ʒu�X�V
				x[i][0] = update_position(x[i][0], v[i][0]);
				x[i][1] = update_position(x[i][1], v[i][1]);
				// ���x�X�V
				v[i][0] = update_velocity(x[i][0], v[i][0], i, 0);
				v[i][1] = update_velocity(x[i][1], v[i][1], i, 1);
				// �p�[�\�i���x�X�g
				update_Pbest(x[i][0], x[i][1], i);

			}

			// �O���[�o���x�X�g
			update_Gbest();

			// �o��
			if (t % 10 == 0) {
				System.out.println(t + "," + Gbest[0] + "," + Gbest[1] + "," + criterion(Gbest[0], Gbest[1]));
			}

		}

	}

	// ����֐�
	public static double criterion(double x1, double x2) {

		double f =x1*x1+x2*x2;

		return f;

	}

	// ������
	public static void initialize() {

		Random rnd = new Random();

		x = new double[num_particle][2];
		v = new double[num_particle][2];
		Pbest = new double[num_particle][2];
		Gbest = new double[2];

		for (int i = 0; i < num_particle; i++) {
			// ���q�̏����ʒu
			x[i][0] = Math.random() * (max_x - min_x) + min_x;
			x[i][1] = Math.random() * (max_x - min_x) + min_x;
			// ���q�̏������x
			v[i][0] = 0;
			v[i][1] = 0;
			// Pbest�̏�����
			Pbest[i][0] = x[i][0];
			Pbest[i][1] = x[i][1];
		}
		// Gbest�̏�����
		Gbest[0] = x[0][0];
		Gbest[1] = x[0][1];

		return;

	}

	// ���q�̈ʒu�̍X�V
	public static double update_position(double x, double v) {

		double new_x = x + v;

		return new_x;

	};

	// ���q�̑��x�̍X�V
	public static double update_velocity(double x, double v, int i, int xy) {

		Random rnd = new Random();

		double ro1 = Math.random() * 1.4;

		double ro2 = Math.random() * 1.4;

		double w = 0.5;

		double new_v = w * v + ro1 * (Pbest[i][xy] - x) + ro2 * (Gbest[xy] - x);

		return new_v;

	};

	public static void update_Pbest(double x, double y, int i) {

		if (criterion(x, y) < criterion(Pbest[i][0], Pbest[i][1])) {

			Pbest[i][0] = x;
			Pbest[i][1] = y;

		}

		return;

	}

	public static void update_Gbest() {

		for (int i = 0; i < num_particle; i++) {

			if (criterion(Pbest[i][0], Pbest[i][1]) < criterion(Gbest[0], Gbest[1])) {

				Gbest[0] = Pbest[i][0];
				Gbest[1] = Pbest[i][1];

			}

		}

	}

}
