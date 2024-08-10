import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../banco-contador.test-samples';

import { BancoContadorFormService } from './banco-contador-form.service';

describe('BancoContador Form Service', () => {
  let service: BancoContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BancoContadorFormService);
  });

  describe('Service methods', () => {
    describe('createBancoContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBancoContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            agencia: expect.any(Object),
            conta: expect.any(Object),
            digitoAgencia: expect.any(Object),
            digitoConta: expect.any(Object),
            principal: expect.any(Object),
            contador: expect.any(Object),
            banco: expect.any(Object),
          }),
        );
      });

      it('passing IBancoContador should create a new form with FormGroup', () => {
        const formGroup = service.createBancoContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            agencia: expect.any(Object),
            conta: expect.any(Object),
            digitoAgencia: expect.any(Object),
            digitoConta: expect.any(Object),
            principal: expect.any(Object),
            contador: expect.any(Object),
            banco: expect.any(Object),
          }),
        );
      });
    });

    describe('getBancoContador', () => {
      it('should return NewBancoContador for default BancoContador initial value', () => {
        const formGroup = service.createBancoContadorFormGroup(sampleWithNewData);

        const bancoContador = service.getBancoContador(formGroup) as any;

        expect(bancoContador).toMatchObject(sampleWithNewData);
      });

      it('should return NewBancoContador for empty BancoContador initial value', () => {
        const formGroup = service.createBancoContadorFormGroup();

        const bancoContador = service.getBancoContador(formGroup) as any;

        expect(bancoContador).toMatchObject({});
      });

      it('should return IBancoContador', () => {
        const formGroup = service.createBancoContadorFormGroup(sampleWithRequiredData);

        const bancoContador = service.getBancoContador(formGroup) as any;

        expect(bancoContador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBancoContador should not enable id FormControl', () => {
        const formGroup = service.createBancoContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBancoContador should disable id FormControl', () => {
        const formGroup = service.createBancoContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
