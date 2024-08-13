import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { TermoDeAdesaoService } from '../service/termo-de-adesao.service';
import { ITermoDeAdesao } from '../termo-de-adesao.model';
import { TermoDeAdesaoFormService } from './termo-de-adesao-form.service';

import { TermoDeAdesaoUpdateComponent } from './termo-de-adesao-update.component';

describe('TermoDeAdesao Management Update Component', () => {
  let comp: TermoDeAdesaoUpdateComponent;
  let fixture: ComponentFixture<TermoDeAdesaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let termoDeAdesaoFormService: TermoDeAdesaoFormService;
  let termoDeAdesaoService: TermoDeAdesaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TermoDeAdesaoUpdateComponent],
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
      .overrideTemplate(TermoDeAdesaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TermoDeAdesaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    termoDeAdesaoFormService = TestBed.inject(TermoDeAdesaoFormService);
    termoDeAdesaoService = TestBed.inject(TermoDeAdesaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const termoDeAdesao: ITermoDeAdesao = { id: 456 };

      activatedRoute.data = of({ termoDeAdesao });
      comp.ngOnInit();

      expect(comp.termoDeAdesao).toEqual(termoDeAdesao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoDeAdesao>>();
      const termoDeAdesao = { id: 123 };
      jest.spyOn(termoDeAdesaoFormService, 'getTermoDeAdesao').mockReturnValue(termoDeAdesao);
      jest.spyOn(termoDeAdesaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoDeAdesao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: termoDeAdesao }));
      saveSubject.complete();

      // THEN
      expect(termoDeAdesaoFormService.getTermoDeAdesao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(termoDeAdesaoService.update).toHaveBeenCalledWith(expect.objectContaining(termoDeAdesao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoDeAdesao>>();
      const termoDeAdesao = { id: 123 };
      jest.spyOn(termoDeAdesaoFormService, 'getTermoDeAdesao').mockReturnValue({ id: null });
      jest.spyOn(termoDeAdesaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoDeAdesao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: termoDeAdesao }));
      saveSubject.complete();

      // THEN
      expect(termoDeAdesaoFormService.getTermoDeAdesao).toHaveBeenCalled();
      expect(termoDeAdesaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITermoDeAdesao>>();
      const termoDeAdesao = { id: 123 };
      jest.spyOn(termoDeAdesaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ termoDeAdesao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(termoDeAdesaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
