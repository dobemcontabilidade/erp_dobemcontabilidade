import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../grupo-acesso-padrao.test-samples';

import { GrupoAcessoPadraoFormService } from './grupo-acesso-padrao-form.service';

describe('GrupoAcessoPadrao Form Service', () => {
  let service: GrupoAcessoPadraoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrupoAcessoPadraoFormService);
  });

  describe('Service methods', () => {
    describe('createGrupoAcessoPadraoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGrupoAcessoPadraoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
          }),
        );
      });

      it('passing IGrupoAcessoPadrao should create a new form with FormGroup', () => {
        const formGroup = service.createGrupoAcessoPadraoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
          }),
        );
      });
    });

    describe('getGrupoAcessoPadrao', () => {
      it('should return NewGrupoAcessoPadrao for default GrupoAcessoPadrao initial value', () => {
        const formGroup = service.createGrupoAcessoPadraoFormGroup(sampleWithNewData);

        const grupoAcessoPadrao = service.getGrupoAcessoPadrao(formGroup) as any;

        expect(grupoAcessoPadrao).toMatchObject(sampleWithNewData);
      });

      it('should return NewGrupoAcessoPadrao for empty GrupoAcessoPadrao initial value', () => {
        const formGroup = service.createGrupoAcessoPadraoFormGroup();

        const grupoAcessoPadrao = service.getGrupoAcessoPadrao(formGroup) as any;

        expect(grupoAcessoPadrao).toMatchObject({});
      });

      it('should return IGrupoAcessoPadrao', () => {
        const formGroup = service.createGrupoAcessoPadraoFormGroup(sampleWithRequiredData);

        const grupoAcessoPadrao = service.getGrupoAcessoPadrao(formGroup) as any;

        expect(grupoAcessoPadrao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGrupoAcessoPadrao should not enable id FormControl', () => {
        const formGroup = service.createGrupoAcessoPadraoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGrupoAcessoPadrao should disable id FormControl', () => {
        const formGroup = service.createGrupoAcessoPadraoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
