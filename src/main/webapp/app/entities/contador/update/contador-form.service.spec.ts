import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../contador.test-samples';

import { ContadorFormService } from './contador-form.service';

describe('Contador Form Service', () => {
  let service: ContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContadorFormService);
  });

  describe('Service methods', () => {
    describe('createContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            crc: expect.any(Object),
            limiteEmpresas: expect.any(Object),
            limiteDepartamentos: expect.any(Object),
            limiteFaturamento: expect.any(Object),
            situacaoContador: expect.any(Object),
            pessoa: expect.any(Object),
            usuarioContador: expect.any(Object),
            perfilContador: expect.any(Object),
          }),
        );
      });

      it('passing IContador should create a new form with FormGroup', () => {
        const formGroup = service.createContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            crc: expect.any(Object),
            limiteEmpresas: expect.any(Object),
            limiteDepartamentos: expect.any(Object),
            limiteFaturamento: expect.any(Object),
            situacaoContador: expect.any(Object),
            pessoa: expect.any(Object),
            usuarioContador: expect.any(Object),
            perfilContador: expect.any(Object),
          }),
        );
      });
    });

    describe('getContador', () => {
      it('should return NewContador for default Contador initial value', () => {
        const formGroup = service.createContadorFormGroup(sampleWithNewData);

        const contador = service.getContador(formGroup) as any;

        expect(contador).toMatchObject(sampleWithNewData);
      });

      it('should return NewContador for empty Contador initial value', () => {
        const formGroup = service.createContadorFormGroup();

        const contador = service.getContador(formGroup) as any;

        expect(contador).toMatchObject({});
      });

      it('should return IContador', () => {
        const formGroup = service.createContadorFormGroup(sampleWithRequiredData);

        const contador = service.getContador(formGroup) as any;

        expect(contador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContador should not enable id FormControl', () => {
        const formGroup = service.createContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContador should disable id FormControl', () => {
        const formGroup = service.createContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
