import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-pessoa.test-samples';

import { AnexoPessoaFormService } from './anexo-pessoa-form.service';

describe('AnexoPessoa Form Service', () => {
  let service: AnexoPessoaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoPessoaFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoPessoaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoPessoaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            urlArquivo: expect.any(Object),
            descricao: expect.any(Object),
            pessoa: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoPessoa should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoPessoaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            urlArquivo: expect.any(Object),
            descricao: expect.any(Object),
            pessoa: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoPessoa', () => {
      it('should return NewAnexoPessoa for default AnexoPessoa initial value', () => {
        const formGroup = service.createAnexoPessoaFormGroup(sampleWithNewData);

        const anexoPessoa = service.getAnexoPessoa(formGroup) as any;

        expect(anexoPessoa).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoPessoa for empty AnexoPessoa initial value', () => {
        const formGroup = service.createAnexoPessoaFormGroup();

        const anexoPessoa = service.getAnexoPessoa(formGroup) as any;

        expect(anexoPessoa).toMatchObject({});
      });

      it('should return IAnexoPessoa', () => {
        const formGroup = service.createAnexoPessoaFormGroup(sampleWithRequiredData);

        const anexoPessoa = service.getAnexoPessoa(formGroup) as any;

        expect(anexoPessoa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoPessoa should not enable id FormControl', () => {
        const formGroup = service.createAnexoPessoaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoPessoa should disable id FormControl', () => {
        const formGroup = service.createAnexoPessoaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
