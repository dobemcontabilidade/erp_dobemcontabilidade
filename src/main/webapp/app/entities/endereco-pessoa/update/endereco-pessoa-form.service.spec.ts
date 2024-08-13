import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../endereco-pessoa.test-samples';

import { EnderecoPessoaFormService } from './endereco-pessoa-form.service';

describe('EnderecoPessoa Form Service', () => {
  let service: EnderecoPessoaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnderecoPessoaFormService);
  });

  describe('Service methods', () => {
    describe('createEnderecoPessoaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEnderecoPessoaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            logradouro: expect.any(Object),
            numero: expect.any(Object),
            complemento: expect.any(Object),
            bairro: expect.any(Object),
            cep: expect.any(Object),
            principal: expect.any(Object),
            residenciaPropria: expect.any(Object),
            pessoa: expect.any(Object),
            cidade: expect.any(Object),
          }),
        );
      });

      it('passing IEnderecoPessoa should create a new form with FormGroup', () => {
        const formGroup = service.createEnderecoPessoaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            logradouro: expect.any(Object),
            numero: expect.any(Object),
            complemento: expect.any(Object),
            bairro: expect.any(Object),
            cep: expect.any(Object),
            principal: expect.any(Object),
            residenciaPropria: expect.any(Object),
            pessoa: expect.any(Object),
            cidade: expect.any(Object),
          }),
        );
      });
    });

    describe('getEnderecoPessoa', () => {
      it('should return NewEnderecoPessoa for default EnderecoPessoa initial value', () => {
        const formGroup = service.createEnderecoPessoaFormGroup(sampleWithNewData);

        const enderecoPessoa = service.getEnderecoPessoa(formGroup) as any;

        expect(enderecoPessoa).toMatchObject(sampleWithNewData);
      });

      it('should return NewEnderecoPessoa for empty EnderecoPessoa initial value', () => {
        const formGroup = service.createEnderecoPessoaFormGroup();

        const enderecoPessoa = service.getEnderecoPessoa(formGroup) as any;

        expect(enderecoPessoa).toMatchObject({});
      });

      it('should return IEnderecoPessoa', () => {
        const formGroup = service.createEnderecoPessoaFormGroup(sampleWithRequiredData);

        const enderecoPessoa = service.getEnderecoPessoa(formGroup) as any;

        expect(enderecoPessoa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEnderecoPessoa should not enable id FormControl', () => {
        const formGroup = service.createEnderecoPessoaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEnderecoPessoa should disable id FormControl', () => {
        const formGroup = service.createEnderecoPessoaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
