import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { PessoaFisicaService } from 'app/entities/pessoa-fisica/service/pessoa-fisica.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IEnderecoPessoa } from '../endereco-pessoa.model';
import { EnderecoPessoaService } from '../service/endereco-pessoa.service';
import { EnderecoPessoaFormService } from './endereco-pessoa-form.service';

import { EnderecoPessoaUpdateComponent } from './endereco-pessoa-update.component';

describe('EnderecoPessoa Management Update Component', () => {
  let comp: EnderecoPessoaUpdateComponent;
  let fixture: ComponentFixture<EnderecoPessoaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let enderecoPessoaFormService: EnderecoPessoaFormService;
  let enderecoPessoaService: EnderecoPessoaService;
  let pessoaFisicaService: PessoaFisicaService;
  let cidadeService: CidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EnderecoPessoaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EnderecoPessoaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnderecoPessoaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enderecoPessoaFormService = TestBed.inject(EnderecoPessoaFormService);
    enderecoPessoaService = TestBed.inject(EnderecoPessoaService);
    pessoaFisicaService = TestBed.inject(PessoaFisicaService);
    cidadeService = TestBed.inject(CidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PessoaFisica query and add missing value', () => {
      const enderecoPessoa: IEnderecoPessoa = { id: 456 };
      const pessoa: IPessoaFisica = { id: 17709 };
      enderecoPessoa.pessoa = pessoa;

      const pessoaFisicaCollection: IPessoaFisica[] = [{ id: 8740 }];
      jest.spyOn(pessoaFisicaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaFisicaCollection })));
      const additionalPessoaFisicas = [pessoa];
      const expectedCollection: IPessoaFisica[] = [...additionalPessoaFisicas, ...pessoaFisicaCollection];
      jest.spyOn(pessoaFisicaService, 'addPessoaFisicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enderecoPessoa });
      comp.ngOnInit();

      expect(pessoaFisicaService.query).toHaveBeenCalled();
      expect(pessoaFisicaService.addPessoaFisicaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaFisicaCollection,
        ...additionalPessoaFisicas.map(expect.objectContaining),
      );
      expect(comp.pessoaFisicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Cidade query and add missing value', () => {
      const enderecoPessoa: IEnderecoPessoa = { id: 456 };
      const cidade: ICidade = { id: 1272 };
      enderecoPessoa.cidade = cidade;

      const cidadeCollection: ICidade[] = [{ id: 7384 }];
      jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
      const additionalCidades = [cidade];
      const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
      jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enderecoPessoa });
      comp.ngOnInit();

      expect(cidadeService.query).toHaveBeenCalled();
      expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(
        cidadeCollection,
        ...additionalCidades.map(expect.objectContaining),
      );
      expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const enderecoPessoa: IEnderecoPessoa = { id: 456 };
      const pessoa: IPessoaFisica = { id: 26183 };
      enderecoPessoa.pessoa = pessoa;
      const cidade: ICidade = { id: 15479 };
      enderecoPessoa.cidade = cidade;

      activatedRoute.data = of({ enderecoPessoa });
      comp.ngOnInit();

      expect(comp.pessoaFisicasSharedCollection).toContain(pessoa);
      expect(comp.cidadesSharedCollection).toContain(cidade);
      expect(comp.enderecoPessoa).toEqual(enderecoPessoa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoPessoa>>();
      const enderecoPessoa = { id: 123 };
      jest.spyOn(enderecoPessoaFormService, 'getEnderecoPessoa').mockReturnValue(enderecoPessoa);
      jest.spyOn(enderecoPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enderecoPessoa }));
      saveSubject.complete();

      // THEN
      expect(enderecoPessoaFormService.getEnderecoPessoa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(enderecoPessoaService.update).toHaveBeenCalledWith(expect.objectContaining(enderecoPessoa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoPessoa>>();
      const enderecoPessoa = { id: 123 };
      jest.spyOn(enderecoPessoaFormService, 'getEnderecoPessoa').mockReturnValue({ id: null });
      jest.spyOn(enderecoPessoaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoPessoa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enderecoPessoa }));
      saveSubject.complete();

      // THEN
      expect(enderecoPessoaFormService.getEnderecoPessoa).toHaveBeenCalled();
      expect(enderecoPessoaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoPessoa>>();
      const enderecoPessoa = { id: 123 };
      jest.spyOn(enderecoPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enderecoPessoaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoaFisica', () => {
      it('Should forward to pessoaFisicaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaFisicaService, 'comparePessoaFisica');
        comp.comparePessoaFisica(entity, entity2);
        expect(pessoaFisicaService.comparePessoaFisica).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCidade', () => {
      it('Should forward to cidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cidadeService, 'compareCidade');
        comp.compareCidade(entity, entity2);
        expect(cidadeService.compareCidade).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
