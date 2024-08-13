import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../perfil-contador.test-samples';

import { PerfilContadorFormService } from './perfil-contador-form.service';

describe('PerfilContador Form Service', () => {
  let service: PerfilContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PerfilContadorFormService);
  });

  describe('Service methods', () => {
    describe('createPerfilContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPerfilContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            perfil: expect.any(Object),
            descricao: expect.any(Object),
            limiteEmpresas: expect.any(Object),
            limiteDepartamentos: expect.any(Object),
            limiteFaturamento: expect.any(Object),
          }),
        );
      });

      it('passing IPerfilContador should create a new form with FormGroup', () => {
        const formGroup = service.createPerfilContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            perfil: expect.any(Object),
            descricao: expect.any(Object),
            limiteEmpresas: expect.any(Object),
            limiteDepartamentos: expect.any(Object),
            limiteFaturamento: expect.any(Object),
          }),
        );
      });
    });

    describe('getPerfilContador', () => {
      it('should return NewPerfilContador for default PerfilContador initial value', () => {
        const formGroup = service.createPerfilContadorFormGroup(sampleWithNewData);

        const perfilContador = service.getPerfilContador(formGroup) as any;

        expect(perfilContador).toMatchObject(sampleWithNewData);
      });

      it('should return NewPerfilContador for empty PerfilContador initial value', () => {
        const formGroup = service.createPerfilContadorFormGroup();

        const perfilContador = service.getPerfilContador(formGroup) as any;

        expect(perfilContador).toMatchObject({});
      });

      it('should return IPerfilContador', () => {
        const formGroup = service.createPerfilContadorFormGroup(sampleWithRequiredData);

        const perfilContador = service.getPerfilContador(formGroup) as any;

        expect(perfilContador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPerfilContador should not enable id FormControl', () => {
        const formGroup = service.createPerfilContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPerfilContador should disable id FormControl', () => {
        const formGroup = service.createPerfilContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
