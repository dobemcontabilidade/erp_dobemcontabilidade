import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { AnexoPessoaService } from '../service/anexo-pessoa.service';
import { IAnexoPessoa } from '../anexo-pessoa.model';
import { AnexoPessoaFormService } from './anexo-pessoa-form.service';

import { AnexoPessoaUpdateComponent } from './anexo-pessoa-update.component';

describe('AnexoPessoa Management Update Component', () => {
  let comp: AnexoPessoaUpdateComponent;
  let fixture: ComponentFixture<AnexoPessoaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let anexoPessoaFormService: AnexoPessoaFormService;
  let anexoPessoaService: AnexoPessoaService;
  let pessoaService: PessoaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AnexoPessoaUpdateComponent],
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
      .overrideTemplate(AnexoPessoaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AnexoPessoaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    anexoPessoaFormService = TestBed.inject(AnexoPessoaFormService);
    anexoPessoaService = TestBed.inject(AnexoPessoaService);
    pessoaService = TestBed.inject(PessoaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pessoa query and add missing value', () => {
      const anexoPessoa: IAnexoPessoa = { id: 456 };
      const pessoa: IPessoa = { id: 16955 };
      anexoPessoa.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ id: 6223 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const additionalPessoas = [pessoa];
      const expectedCollection: IPessoa[] = [...additionalPessoas, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ anexoPessoa });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoaCollection,
        ...additionalPessoas.map(expect.objectContaining),
      );
      expect(comp.pessoasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const anexoPessoa: IAnexoPessoa = { id: 456 };
      const pessoa: IPessoa = { id: 10515 };
      anexoPessoa.pessoa = pessoa;

      activatedRoute.data = of({ anexoPessoa });
      comp.ngOnInit();

      expect(comp.pessoasSharedCollection).toContain(pessoa);
      expect(comp.anexoPessoa).toEqual(anexoPessoa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoPessoa>>();
      const anexoPessoa = { id: 123 };
      jest.spyOn(anexoPessoaFormService, 'getAnexoPessoa').mockReturnValue(anexoPessoa);
      jest.spyOn(anexoPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoPessoa }));
      saveSubject.complete();

      // THEN
      expect(anexoPessoaFormService.getAnexoPessoa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(anexoPessoaService.update).toHaveBeenCalledWith(expect.objectContaining(anexoPessoa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoPessoa>>();
      const anexoPessoa = { id: 123 };
      jest.spyOn(anexoPessoaFormService, 'getAnexoPessoa').mockReturnValue({ id: null });
      jest.spyOn(anexoPessoaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoPessoa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: anexoPessoa }));
      saveSubject.complete();

      // THEN
      expect(anexoPessoaFormService.getAnexoPessoa).toHaveBeenCalled();
      expect(anexoPessoaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAnexoPessoa>>();
      const anexoPessoa = { id: 123 };
      jest.spyOn(anexoPessoaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ anexoPessoa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(anexoPessoaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoa', () => {
      it('Should forward to pessoaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaService, 'comparePessoa');
        comp.comparePessoa(entity, entity2);
        expect(pessoaService.comparePessoa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
