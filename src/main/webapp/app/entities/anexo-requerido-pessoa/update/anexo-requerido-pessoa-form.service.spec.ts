import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-requerido-pessoa.test-samples';

import { AnexoRequeridoPessoaFormService } from './anexo-requerido-pessoa-form.service';

describe('AnexoRequeridoPessoa Form Service', () => {
  let service: AnexoRequeridoPessoaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoRequeridoPessoaFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoRequeridoPessoaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoRequeridoPessoaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            tipo: expect.any(Object),
            anexoPessoa: expect.any(Object),
            anexoRequerido: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoRequeridoPessoa should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoRequeridoPessoaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            tipo: expect.any(Object),
            anexoPessoa: expect.any(Object),
            anexoRequerido: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoRequeridoPessoa', () => {
      it('should return NewAnexoRequeridoPessoa for default AnexoRequeridoPessoa initial value', () => {
        const formGroup = service.createAnexoRequeridoPessoaFormGroup(sampleWithNewData);

        const anexoRequeridoPessoa = service.getAnexoRequeridoPessoa(formGroup) as any;

        expect(anexoRequeridoPessoa).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoRequeridoPessoa for empty AnexoRequeridoPessoa initial value', () => {
        const formGroup = service.createAnexoRequeridoPessoaFormGroup();

        const anexoRequeridoPessoa = service.getAnexoRequeridoPessoa(formGroup) as any;

        expect(anexoRequeridoPessoa).toMatchObject({});
      });

      it('should return IAnexoRequeridoPessoa', () => {
        const formGroup = service.createAnexoRequeridoPessoaFormGroup(sampleWithRequiredData);

        const anexoRequeridoPessoa = service.getAnexoRequeridoPessoa(formGroup) as any;

        expect(anexoRequeridoPessoa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoRequeridoPessoa should not enable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoPessoaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoRequeridoPessoa should disable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoPessoaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
