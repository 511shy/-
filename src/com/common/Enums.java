package com.common;

import com.common.Enums;
import com.common.Enums.EmnuAdminState;

//�������ר������װ�������õ��ĸ���õ��,���������õ��ĳ����ĳ���
public final class Enums {
	//��ʾҳ���С
	public final static int PAGESIZE=10;
	
	private Enums(){}
	
	enum  weekday {
		 ��һ,
		 �ܶ�,
		 ����,
		 ����,
		 ���� 
	}

	//���ʽ
	public enum EnumPayMethod{
		��������ת��,
		�ʾֻ��,
		�����Ͳ�����,
		�������ʽ
	}
	
	//�ʵݷ�ʽ
	public enum EnumPostMethod{
		ƽ��,
		���,
		EMS,
		��������
	}
	
	//��ʾ��Ա�ȼ�
	public enum EmnuMemberLevel{
		�ƽ��Ա,
		������Ա,
		��ͭ��Ա,
		��ͨ��Ա
	}
	
	//��ʾ����Ա״̬
	public enum EmnuAdminState {

		���� {
			public String getValue() {
				return "1";
			}
		},

		
		ɾ��{
			public String getValue() {
				return "0";
			}
		},

		
		���� {
			public String getValue() {

				return "2";
			}
		};

		public abstract String getValue();
		
	}


	//��ʾ����״̬
	public enum EnumOrderState {
		
		  δ���� {
			public String getValue() {
				return "δ����";
			}
		},

		
		�ѷ��� {
			public String getValue() {

				return "�ѷ���";
			}
		},

		��֧��
		{
			public String getValue() {
				return "��֧��";
			}
		};   //���һ��,�����Ƿֺ�

		public abstract String getValue();
	}

	
	public static void main(String[] args) {
		EmnuAdminState [] data = Enums.EmnuAdminState.values();
		
		for(EmnuAdminState  i:data){
			System.out.println(i+":"+i.getValue());
		}
	}

}
