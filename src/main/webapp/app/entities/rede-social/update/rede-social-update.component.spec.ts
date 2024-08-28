import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { RedeSocialService } from '../service/rede-social.service';
import { IRedeSocial } from '../rede-social.model';
import { RedeSocialFormService } from './rede-social-form.service';

import { RedeSocialUpdateComponent } from './rede-social-update.component';

describe('RedeSocial Management Update Component', () => {
  let comp: RedeSocialUpdateComponent;
  let fixture: ComponentFixture<RedeSocialUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let redeSocialFormService: RedeSocialFormService;
  let redeSocialService: RedeSocialService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RedeSocialUpdateComponent],
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
      .overrideTemplate(RedeSocialUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RedeSocialUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    redeSocialFormService = TestBed.inject(RedeSocialFormService);
    redeSocialService = TestBed.inject(RedeSocialService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const redeSocial: IRedeSocial = { id: 456 };

      activatedRoute.data = of({ redeSocial });
      comp.ngOnInit();

      expect(comp.redeSocial).toEqual(redeSocial);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRedeSocial>>();
      const redeSocial = { id: 123 };
      jest.spyOn(redeSocialFormService, 'getRedeSocial').mockReturnValue(redeSocial);
      jest.spyOn(redeSocialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ redeSocial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: redeSocial }));
      saveSubject.complete();

      // THEN
      expect(redeSocialFormService.getRedeSocial).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(redeSocialService.update).toHaveBeenCalledWith(expect.objectContaining(redeSocial));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRedeSocial>>();
      const redeSocial = { id: 123 };
      jest.spyOn(redeSocialFormService, 'getRedeSocial').mockReturnValue({ id: null });
      jest.spyOn(redeSocialService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ redeSocial: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: redeSocial }));
      saveSubject.complete();

      // THEN
      expect(redeSocialFormService.getRedeSocial).toHaveBeenCalled();
      expect(redeSocialService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRedeSocial>>();
      const redeSocial = { id: 123 };
      jest.spyOn(redeSocialService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ redeSocial });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(redeSocialService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
