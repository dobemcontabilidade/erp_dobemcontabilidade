import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../perfil-contador-departamento.test-samples';

import { PerfilContadorDepartamentoFormService } from './perfil-contador-departamento-form.service';

describe('PerfilContadorDepartamento Form Service', () => {
  let service: PerfilContadorDepartamentoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PerfilContadorDepartamentoFormService);
  });

  describe('Service methods', () => {
    describe('createPerfilContadorDepartamentoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPerfilContadorDepartamentoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantidadeEmpresas: expect.any(Object),
            percentualExperiencia: expect.any(Object),
            departamento: expect.any(Object),
            perfilContador: expect.any(Object),
          }),
        );
      });

      it('passing IPerfilContadorDepartamento should create a new form with FormGroup', () => {
        const formGroup = service.createPerfilContadorDepartamentoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantidadeEmpresas: expect.any(Object),
            percentualExperiencia: expect.any(Object),
            departamento: expect.any(Object),
            perfilContador: expect.any(Object),
          }),
        );
      });
    });

    describe('getPerfilContadorDepartamento', () => {
      it('should return NewPerfilContadorDepartamento for default PerfilContadorDepartamento initial value', () => {
        const formGroup = service.createPerfilContadorDepartamentoFormGroup(sampleWithNewData);

        const perfilContadorDepartamento = service.getPerfilContadorDepartamento(formGroup) as any;

        expect(perfilContadorDepartamento).toMatchObject(sampleWithNewData);
      });

      it('should return NewPerfilContadorDepartamento for empty PerfilContadorDepartamento initial value', () => {
        const formGroup = service.createPerfilContadorDepartamentoFormGroup();

        const perfilContadorDepartamento = service.getPerfilContadorDepartamento(formGroup) as any;

        expect(perfilContadorDepartamento).toMatchObject({});
      });

      it('should return IPerfilContadorDepartamento', () => {
        const formGroup = service.createPerfilContadorDepartamentoFormGroup(sampleWithRequiredData);

        const perfilContadorDepartamento = service.getPerfilContadorDepartamento(formGroup) as any;

        expect(perfilContadorDepartamento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPerfilContadorDepartamento should not enable id FormControl', () => {
        const formGroup = service.createPerfilContadorDepartamentoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPerfilContadorDepartamento should disable id FormControl', () => {
        const formGroup = service.createPerfilContadorDepartamentoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
