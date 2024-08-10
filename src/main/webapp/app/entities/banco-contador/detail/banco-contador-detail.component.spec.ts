import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { BancoContadorDetailComponent } from './banco-contador-detail.component';

describe('BancoContador Management Detail Component', () => {
  let comp: BancoContadorDetailComponent;
  let fixture: ComponentFixture<BancoContadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BancoContadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: BancoContadorDetailComponent,
              resolve: { bancoContador: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BancoContadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BancoContadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bancoContador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BancoContadorDetailComponent);

      // THEN
      expect(instance.bancoContador()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
