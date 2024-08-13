import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../perfil-acesso.test-samples';

import { PerfilAcessoFormService } from './perfil-acesso-form.service';

describe('PerfilAcesso Form Service', () => {
  let service: PerfilAcessoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PerfilAcessoFormService);
  });

  describe('Service methods', () => {
    describe('createPerfilAcessoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPerfilAcessoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing IPerfilAcesso should create a new form with FormGroup', () => {
        const formGroup = service.createPerfilAcessoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getPerfilAcesso', () => {
      it('should return NewPerfilAcesso for default PerfilAcesso initial value', () => {
        const formGroup = service.createPerfilAcessoFormGroup(sampleWithNewData);

        const perfilAcesso = service.getPerfilAcesso(formGroup) as any;

        expect(perfilAcesso).toMatchObject(sampleWithNewData);
      });

      it('should return NewPerfilAcesso for empty PerfilAcesso initial value', () => {
        const formGroup = service.createPerfilAcessoFormGroup();

        const perfilAcesso = service.getPerfilAcesso(formGroup) as any;

        expect(perfilAcesso).toMatchObject({});
      });

      it('should return IPerfilAcesso', () => {
        const formGroup = service.createPerfilAcessoFormGroup(sampleWithRequiredData);

        const perfilAcesso = service.getPerfilAcesso(formGroup) as any;

        expect(perfilAcesso).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPerfilAcesso should not enable id FormControl', () => {
        const formGroup = service.createPerfilAcessoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPerfilAcesso should disable id FormControl', () => {
        const formGroup = service.createPerfilAcessoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
