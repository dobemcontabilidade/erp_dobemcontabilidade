import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../atividade-empresa.test-samples';

import { AtividadeEmpresaFormService } from './atividade-empresa-form.service';

describe('AtividadeEmpresa Form Service', () => {
  let service: AtividadeEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AtividadeEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createAtividadeEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAtividadeEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            principal: expect.any(Object),
            ordem: expect.any(Object),
            descricaoAtividade: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });

      it('passing IAtividadeEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createAtividadeEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            principal: expect.any(Object),
            ordem: expect.any(Object),
            descricaoAtividade: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getAtividadeEmpresa', () => {
      it('should return NewAtividadeEmpresa for default AtividadeEmpresa initial value', () => {
        const formGroup = service.createAtividadeEmpresaFormGroup(sampleWithNewData);

        const atividadeEmpresa = service.getAtividadeEmpresa(formGroup) as any;

        expect(atividadeEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewAtividadeEmpresa for empty AtividadeEmpresa initial value', () => {
        const formGroup = service.createAtividadeEmpresaFormGroup();

        const atividadeEmpresa = service.getAtividadeEmpresa(formGroup) as any;

        expect(atividadeEmpresa).toMatchObject({});
      });

      it('should return IAtividadeEmpresa', () => {
        const formGroup = service.createAtividadeEmpresaFormGroup(sampleWithRequiredData);

        const atividadeEmpresa = service.getAtividadeEmpresa(formGroup) as any;

        expect(atividadeEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAtividadeEmpresa should not enable id FormControl', () => {
        const formGroup = service.createAtividadeEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAtividadeEmpresa should disable id FormControl', () => {
        const formGroup = service.createAtividadeEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
